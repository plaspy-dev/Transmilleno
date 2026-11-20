from argparse import ArgumentParser
import os
from pathlib import Path
import shutil
import subprocess
import sys
from xml.etree import ElementTree


def run(command, cwd=None):
    args = [str(argument) for argument in command]
    print('> ' + subprocess.list2cmdline(args), flush=True)
    subprocess.run(args, cwd=cwd, check=True, timeout=90)


def java_sources(root):
    return sorted(root.rglob('*.java'))


def midp(project, output, java, emulator):
    output.mkdir(parents=True, exist_ok=True)
    classes = output / 'classes'
    classes.mkdir(exist_ok=True)
    sources = java_sources(project)
    run([java / 'javac.exe', '-encoding', 'windows-1252', '-source', '7', '-target', '7', '-cp', emulator,
         '-d', classes, *sources])
    midlet = next(path for path in sources if path.name.casefold() == 'transmilenio.java')
    package = next((line.split()[1].rstrip(';') for line in midlet.read_text(encoding='cp1252').splitlines()
                    if line.startswith('package ')), '')
    app_class = (package + '.' if package else '') + midlet.stem
    manifest = output / 'MANIFEST.MF'
    manifest.write_text('Manifest-Version: 1.0\nMIDlet-Name: Transmilleno\nMIDlet-Vendor: Transmilleno\n'
                        'MIDlet-Version: 1.0.0\nMicroEdition-Profile: MIDP-2.0\nMicroEdition-Configuration: CLDC-1.1\n'
                        f'MIDlet-1: Transmilleno,,{app_class}\n\n', encoding='ascii')
    resources = project / 'res'
    parameters = ['-C', resources, '.'] if resources.is_dir() else []
    run([java / 'jar.exe', 'cfm', output / 'Transmilleno.jar', manifest, '-C', classes, '.', *parameters])


def android_bundle(site, target):
    import base64
    import mimetypes
    import re

    page = (site / 'm.html').read_text(encoding='utf-8-sig')
    zone_link = re.compile(r'(<a\b[^>]*href="#pzona([a-z])"[^>]*>)[^<]+(</a>)', re.I)
    page = zone_link.sub(lambda link: link[1] + 'Zona Demo ' + link[2].upper() + link[3], page)
    zone_heading = re.compile(r'(<div\b[^>]*id="pzona([a-z])"[^>]*>(?:(?!<div\b[^>]*id="pzona).)*?<h1\b[^>]*>)[^<]*(</h1>)', re.I | re.S)
    page = zone_heading.sub(lambda heading: heading[1] + 'Zona Demo ' + heading[2].upper() + heading[3], page)

    def script(match):
        code = (site / match[1]).read_text(encoding='utf-8-sig')
        return '<script>' + re.sub(r'</script', lambda _: r'<\/script', code, flags=re.I) + '</script>'

    def stylesheet(match):
        css = site / match[1]
        def urls(url):
            image = css.parent / url[1]
            if not image.resolve().is_relative_to(site.resolve()) or not image.is_file():
                return url[0]
            mime = mimetypes.guess_type(image.name)[0] or 'application/octet-stream'
            encoded = base64.b64encode(image.read_bytes()).decode('ascii')
            return 'url(data:' + mime + ';base64,' + encoded + ')'
        code = re.sub(r'url\(["\']?([a-zA-Z0-9_./-]+)["\']?\)', urls, css.read_text(encoding='utf-8-sig'))
        return '<style>' + code + '</style>'

    page = re.sub(r'<script\b[^>]*src="(js/[^"]+)"[^>]*>\s*</script>', script, page)
    page = re.sub(r'<link\b[^>]*href="(css/[^"]+)"[^>]*/?>', stylesheet, page)
    target.write_text(page, encoding='utf-8', newline='\n')


def android(project, output, sdk, java, modern_java, external):
    output.mkdir(parents=True, exist_ok=True)
    libs = []
    imports = b'\n'.join(source.read_bytes() for source in java_sources(project / 'src'))
    for marker, name in ((b'org.apache.cordova', 'cordova-2.0.0.jar'), (b'com.google.ads', 'GoogleAdMobAdsSdk-6.1.0.jar')):
        if marker in imports:
            library = Path(__file__).resolve().parents[1] / "vendor" / "android" / name
            if not library.is_file():
                raise FileNotFoundError(library)
            libs.append(library)
    manifest = project / 'AndroidManifest.xml'
    if not imports:
        raise ValueError(f'No activity source accompanying {manifest}')
    tools = sdk / 'build-tools' / '35.0.0'
    android_jar = sdk / 'platforms' / 'android-35' / 'android.jar'
    gen = output / 'generated'
    classes = output / 'classes'
    dex = output / 'dex'
    for folder in (gen, classes, dex):
        folder.mkdir(exist_ok=True)
    run([tools / 'aapt2.exe', 'compile', '--dir', project / 'res', '-o', output / 'resources.zip'])
    apk = output / 'Transmilleno-unsigned.apk'
    assets = project / 'assets'
    if project.name in ('Transmilleno', 'Transmilleno Free'):
        # Reuse the recovered route viewer and its fictional catalog offline.
        assets = output / 'offline-assets'
        assets.mkdir(parents=True)
        android_bundle(project.parents[1] / 'web' / 'web', assets / 'offline.html')
    extra = ['-A', assets] if assets.is_dir() else []
    run([tools / 'aapt2.exe', 'link', '-I', android_jar, '--manifest', manifest, '--java', gen, '-o', apk,
         '-R', output / 'resources.zip', '--auto-add-overlay', *extra])
    run([java / 'javac.exe', '-encoding', 'windows-1252', '-source', '7', '-target', '7', '-cp', os.pathsep.join(
        str(path) for path in [android_jar, *libs]), '-d', classes, *java_sources(gen), *java_sources(project / 'src')])
    class_jar = output / 'classes.jar'
    run([java / 'jar.exe', 'cf', class_jar, '-C', classes, '.'])
    run([modern_java / 'java.exe', '-cp', tools / 'lib' / 'd8.jar', 'com.android.tools.r8.D8', '--lib', android_jar,
         '--min-api', '7', '--output', dex, class_jar, *libs])
    run([tools / 'aapt.exe', 'add', apk, 'classes.dex'], cwd=dex)
    keystore = output / 'demo.jks'
    if not keystore.exists():
        run([java / 'keytool.exe', '-genkeypair', '-keystore', keystore, '-storepass', 'android', '-alias',
             'test', '-keypass', 'android', '-dname', 'CN=Local Demo', '-keyalg', 'RSA', '-validity', '30'])
    aligned = output / 'Transmilleno-aligned.apk'
    run([tools / 'zipalign.exe', '-f', '4', apk, aligned])
    run([modern_java / 'java.exe', '-jar', tools / 'lib' / 'apksigner.jar', 'sign', '--ks', keystore, '--ks-pass',
         'pass:android', '--out', output / 'Transmilleno.apk', aligned])


def web(root, output):
    docker = shutil.which('docker')
    php_files = sorted(root.rglob('*.php'))
    js_files = sorted(root.rglob('*.js'))
    if php_files:
        run([docker, 'run', '--rm', '--network', 'none', '--mount', f'type=bind,source={root},target=/src,readonly',
             'php:5.6-cli', 'sh', '-ec', "find /src -name '*.php' -print0 | xargs -0 -n 1 php -d short_open_tag=1 -l"])
    if js_files:
        node = shutil.which('node')
        if not node:
            raise FileNotFoundError('Node.js executable')
        for path in js_files:
            run([node, '--check', path])


def main():
    parser = ArgumentParser(description='Build the historical sources in this checkout for local verification')
    parser.add_argument('--output', type=Path, required=True)
    parser.add_argument('--jdk8', type=Path)
    parser.add_argument('--jdk21', type=Path)
    parser.add_argument('--sdk', type=Path)
    parser.add_argument('--external', type=Path)
    args = parser.parse_args()
    root = Path(__file__).resolve().parents[1]
    output = args.output.resolve()
    external = args.external.resolve() if args.external else root / 'vendor/android'
    assert not output.is_relative_to(root), 'Use an output directory outside the checkout'
    java = args.jdk8.resolve() / 'bin' if args.jdk8 else None
    android_projects = sorted(path.parent for path in root.rglob('AndroidManifest.xml'))
    midp_projects = sorted({(path.parent.parent if path.parent.parent.name == 'src' else path.parent)
                            for path in root.rglob('transmilenio.java') if 'javax.microedition' in path.read_text(encoding='cp1252')})
    if android_projects and not (args.jdk8 and args.jdk21 and args.sdk):
        parser.error('Android requires --jdk8, --jdk21 and --sdk')
    if midp_projects and not (args.jdk8 and args.external):
        parser.error('Java ME requires --jdk8 and --external')
    tasks = []
    for i, project in enumerate(android_projects):
        tasks.append((str(project.relative_to(root)), android,
                      [project, output / f'android-{i}', args.sdk.resolve(), java, args.jdk21.resolve() / 'bin', external]))
    for i, project in enumerate(midp_projects):
        tasks.append((str(project.relative_to(root)), midp, [project, output / f'midp-{i}', java,
                                                            external / 'microemulator-2.0.4.jar']))
    if any(root.rglob('*.php')) or any(root.rglob('*.js')):
        tasks.append(('PHP and JavaScript', web, [root, output]))
    failed = []
    for name, build, parameters in tasks:
        print(f'\nBUILD {name}', flush=True)
        try:
            build(*parameters)
        except (FileNotFoundError, ValueError, subprocess.SubprocessError) as error:
            failed.append(name)
            print(f'FAIL {name}: {error}', flush=True)
        else:
            print(f'OK {name}', flush=True)
    if not tasks:
        raise ValueError('No build entries in this revision')
    sys.exit(bool(failed))


if __name__ == '__main__':
    main()
