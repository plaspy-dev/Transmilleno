# Transmilleno
[English](#english) · [Español](#español)

## English

Android application for finding bus routes between an origin stop and a destination

> [!WARNING]
> **Status: Archived.** Dated editions with local verification steps.

### Overview

Android application for finding bus routes between an origin stop and a destination.

### Features

- Pick an origin and destination in the native Android lists and compare the routes and stops shown in results.
- Open the mobile route interface from a Cordova WebView in the Full and Free editions.

### Requirements

Python 3.12.4 runs the build script. Build Android with JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 and Build Tools 35.0.0. The archived Android JARs required by this edition are in `vendor/android/`.

### Getting started

Run `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk>` from this checkout. The script builds the editions present in the tree.

### Usage

Open the app, pick the origin zone and stop, choose a destination and read the routes in the results list.

Install a generated Full or Free APK on a test emulator to open its entry page. See the verification limits for this edition.

### Project structure

Folders in this edition include `Transmilleno Classic/`, `Transmilleno Free/`, `demo/`, `transmilleno/`, `vendor/`.

### Verification

The archived Android activities use fictional AdMob identifiers. The route trial runs offline.

Built on 20 November 2026 on Windows 10.0.26300 from the source edition dated 2012-09-17. Classic opened the results after two selections in the offline Android 35 emulator. Full and Free build. On Android 35 some editions exit during startup and others lack a working local navigation page.

### History

The source edition keeps its original date of 2012-09-17.

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

- Elige origen y destino en las listas de Android y compara las rutas y el número de paradas que aparecen.
- Abre la interfaz móvil de rutas en la vista web de Cordova de las ediciones Full y Free.

### Requisitos

Python 3.12.4 ejecuta el script de compilación. Compila Android con JDK 8u512-b09, JDK 21.0.13+9, Android SDK API 35 y Build Tools 35.0.0. Los JAR Android que pide esta edición están en `vendor/android/`.

### Cómo empezar

Ejecuta `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --jdk21 <jdk21> --sdk <android-sdk>` desde esta copia. El script compila las ediciones que están en el árbol.

### Uso

Abre la aplicación, elige la zona y parada de origen, selecciona el destino y consulta la lista de recorridos.

Instala un APK Full o Free en un emulador de prueba para abrir su página inicial. Consulta los límites de verificación de esta edición.

### Estructura del proyecto

Las carpetas de esta edición incluyen `Transmilleno Classic/`, `Transmilleno Free/`, `demo/`, `transmilleno/`, `vendor/`.

### Verificación

Las actividades archivadas de Android usan identificadores ficticios de AdMob. La prueba de rutas se ejecuta sin conexión.

Compilado el 20 de noviembre de 2026 en Windows 10.0.26300 desde la edición del 2012-09-17. Classic abrió los resultados después de elegir dos paradas en el emulador Android 35 sin conexión. Full y Free compilan. En Android 35 algunos cortes se cierran al iniciar y otros carecen de una página local que permita recorrer la aplicación.

### Historial

La edición conserva su fecha original del 2012-09-17.

### Soporte

Reproduce el problema en local e indica la edición y el registro correspondiente.

### Contribuir

Conserva los avisos originales y usa ejemplos ficticios en las pruebas.

### Licencia

Consulta la [licencia MIT](LICENSE) y los avisos de las dependencias.
