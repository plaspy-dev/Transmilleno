# Transmilleno
[English](#english) · [Español](#español)

## English

Android application for finding bus routes between an origin stop and a destination

> [!WARNING]
> **Status: Archived.** Dated editions with local verification steps.

### Overview

Android application for finding bus routes between an origin stop and a destination.

### Features

- Open the mobile route interface from a Cordova WebView in the Full and Free editions.
- Select stops on a Java ME phone and calculate available connections from the bundled catalogue.
- Open the desktop or mobile route page selected for the browser.

### Requirements

Python 3.12.4 runs the build script. Build Android with JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 and Build Tools 35.0.0. The archived Android JARs required by this edition are in `vendor/android/`. Add MicroEmulator 2.0.4 to the folder passed to `--external` for Java ME. The web build uses Docker with PHP 5.6.40, Docker Engine 29.9.2 and Node.js 24.13.1 for syntax checks.

### Getting started

Run `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk> --external <external-jars>` from this checkout. The script builds the editions present in the tree.

### Usage

Open the app, pick the origin zone and stop, choose a destination and read the routes in the results list.

Install a generated Full or Free APK on a test emulator to open its entry page. See the verification limits for this edition.

### Project structure

- `android/`. Android manifests, archived Full and Free activities and the Android viewer resources.
- `demo/`. Build and validation script compilar.py.
- `java/`. Java ME route sources. Build the MIDlet via the demo build script.
- `vendor/`. Archived Android build inputs and their original notices.
- `web/`. Browser route viewer, styles and data. Open `web/final/m.html` to view the route selector. The draft and expanded copies are in `web/desarrollo/` and `web/finalcompleto/`.

### Verification

Built on 20 November 2026 on Windows 10.0.26300 from the August 2012 sources and the documented 2026 updates. Full and Free build. On Android 35 some editions exit during startup and others lack a working local navigation page. The original Java ME route model returned an itinerary between fictional stops. The MIDlet interface remains to be fully exercised. The browser route interface is preserved. Its end-to-end route selection remains unverified.

### History

These browser and Android sources date to the August 2012 archive.

### Support

Reproduce the issue locally and include the edition and its relevant log.

### Contributing

Keep original notices and use fictional examples in any test.

### License

See the [MIT license](LICENSE) and the notices on the dependencies.

---

## Español

Aplicación Android para consultar rutas de buses entre una parada de origen y un destino

> [!WARNING]
> **Estado: Archivado.** Ediciones por fecha con pasos de prueba local.

### Descripción general

Aplicación Android para consultar rutas de buses entre una parada de origen y un destino.

### Funciones

- Abre la interfaz móvil de rutas en la vista web de Cordova de las ediciones Full y Free.
- Selecciona paradas en un teléfono Java ME y calcula las conexiones disponibles en el catálogo.
- Abre la página de rutas de escritorio o móvil elegida para el navegador.

### Requisitos

Python 3.12.4 ejecuta el script de compilación. Compila Android con JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 y Build Tools 35.0.0. Los JAR Android que pide esta edición están en `vendor/android/`. Para Java ME, coloca MicroEmulator 2.0.4 en la carpeta indicada en `--external`. La compilación web comprueba sintaxis con Docker Engine 29.9.2, PHP 5.6.40 y Node.js 24.13.1.

### Cómo empezar

Ejecuta `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk> --external <external-jars>` desde esta copia. El script compila las ediciones que están en el árbol.

### Uso

Abre la aplicación, elige la zona y parada de origen, selecciona el destino y consulta la lista de recorridos.

Instala un APK Full o Free en un emulador de prueba para abrir su página inicial. Consulta los límites de verificación de esta edición.

### Estructura del proyecto

- `android/`. Manifiestos Android, actividades de Full y Free y recursos del visor Android.
- `demo/`. Script de compilación y validación compilar.py.
- `java/`. Fuentes de rutas Java ME. Compila el MIDlet con el script de demostración.
- `vendor/`. Insumos archivados para compilar Android y sus avisos originales.
- `web/`. Visor de rutas para navegador, estilos y datos. Abre `web/final/m.html` para ver el selector de recorridos. El borrador y la copia ampliada están en `web/desarrollo/` y `web/finalcompleto/`.

### Verificación

Compilado el 20 de noviembre de 2026 en Windows 10.0.26300 a partir de las fuentes de agosto de 2012 y los cambios de 2026 documentados para este corte. Full y Free compilan. En Android 35 algunos cortes se cierran al iniciar y otros carecen de una página local que permita recorrer la aplicación. El modelo Java ME original devolvió un recorrido entre paradas ficticias. Queda por probar la interfaz completa del MIDlet. Se conserva la interfaz web de rutas. Falta comprobar la selección completa de un recorrido.

### Historial

Estas fuentes de navegador y Android son del respaldo de agosto de 2012.

### Soporte

Reproduce el problema en local e indica la edición y el registro correspondiente.

### Contribuir

Conserva los avisos originales y usa ejemplos ficticios en las pruebas.

### Licencia

Consulta la [licencia MIT](LICENSE) y los avisos de las dependencias.
