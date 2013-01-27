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

### Requirements

Python 3.12.4 runs the build script. Build Android with JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 and Build Tools 35.0.0. The archived Android JARs required by this edition are in `vendor/android/`.

### Getting started

Run `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk>` from this checkout. The script builds the editions present in the tree.

### Usage

Install a generated Full or Free APK on a test emulator to open its entry page. See the verification limits for this edition.

### Project structure

Folders in this edition include `Transmilleno Free/`, `demo/`, `transmilleno/`, `vendor/`.

### Verification

The archived Android activities use fictional AdMob identifiers. The route trial runs offline.

Built on 20 November 2026 on Windows 10.0.26300 from the source edition dated 2013-01-27. Full and Free build. On Android 35 some editions exit during startup and others lack a working local navigation page.

### History

The source edition keeps its original date of 2013-01-27.

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

### Requisitos

Python 3.12.4 ejecuta el script de compilación. Compila Android con JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 y Build Tools 35.0.0. Los JAR Android que pide esta edición están en `vendor/android/`.

### Cómo empezar

Ejecuta `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk>` desde esta copia. El script compila las ediciones que están en el árbol.

### Uso

Instala un APK Full o Free en un emulador de prueba para abrir su página inicial. Consulta los límites de verificación de esta edición.

### Estructura del proyecto

Las carpetas de esta edición incluyen `Transmilleno Free/`, `demo/`, `transmilleno/`, `vendor/`.

### Verificación

Las actividades archivadas de Android usan identificadores ficticios de AdMob. La prueba de rutas se ejecuta sin conexión.

Compilado el 20 de noviembre de 2026 en Windows 10.0.26300 desde la edición del 2013-01-27. Full y Free compilan. En Android 35 algunos cortes se cierran al iniciar y otros carecen de una página local que permita recorrer la aplicación.

### Historial

La edición conserva su fecha original del 2013-01-27.

### Soporte

Reproduce el problema en local e indica la edición y el registro correspondiente.

### Contribuir

Conserva los avisos originales y usa ejemplos ficticios en las pruebas.

### Licencia

Consulta la [licencia MIT](LICENSE) y los avisos de las dependencias.
