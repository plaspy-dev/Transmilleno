# Transmilleno
[English](#english) · [Español](#español)

## English

Route finder for Java ME phones that uses an onboard catalogue of stops and connections

> [!WARNING]
> **Status: Archived.** Dated editions with local verification steps.

### Overview

Route finder for Java ME phones that uses an onboard catalogue of stops and connections.

### Features

- Select stops on a Java ME phone and calculate available connections from the bundled catalogue.

### Requirements

Python 3.12.4 runs the build script. Add MicroEmulator 2.0.4 to the folder passed to `--external` for Java ME.

### Getting started

Run `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --external <external-jars>` from this checkout. The script builds the editions present in the tree.

### Usage

Open the app, pick the origin zone and stop, choose a destination and read the routes in the results list.

### Project structure

Folders in this edition include `demo/`, `transmilleno/`.

### Verification

Built on 20 November 2026 on Windows 10.0.26300 from the source edition dated 2012-09-04. The original Java ME route model returned an itinerary between fictional stops. The MIDlet interface remains to be fully exercised.

### History

The source edition keeps its original date of 2012-09-04.

### Support

Reproduce the issue locally and include the edition and its relevant log.

### Contributing

Keep original notices and use fictional examples in any test.

### License

See the [MIT license](LICENSE) and the notices on the dependencies.

---

## Español

Consulta de rutas para teléfonos Java ME con un catálogo local de paradas y conexiones

> [!WARNING]
> **Estado: Archivado.** Ediciones por fecha con pasos de prueba local.

### Descripción general

Consulta de rutas para teléfonos Java ME con un catálogo local de paradas y conexiones.

### Funciones

- Selecciona paradas en un teléfono Java ME y calcula las conexiones disponibles en el catálogo.

### Requisitos

Python 3.12.4 ejecuta el script de compilación. Para Java ME, coloca MicroEmulator 2.0.4 en la carpeta indicada en `--external`.

### Cómo empezar

Ejecuta `python demo/compilar.py --output <new-folder> --jdk8 <jdk8> --external <external-jars>` desde esta copia. El script compila las ediciones que están en el árbol.

### Uso

Abre la aplicación, elige la zona y parada de origen, selecciona el destino y consulta la lista de recorridos.

### Estructura del proyecto

Las carpetas de esta edición incluyen `demo/`, `transmilleno/`.

### Verificación

Compilado el 20 de noviembre de 2026 en Windows 10.0.26300 desde la edición del 2012-09-04. El modelo Java ME original devolvió un recorrido entre paradas ficticias. Queda por probar la interfaz completa del MIDlet.

### Historial

La edición conserva su fecha original del 2012-09-04.

### Soporte

Reproduce el problema en local e indica la edición y el registro correspondiente.

### Contribuir

Conserva los avisos originales y usa ejemplos ficticios en las pruebas.

### Licencia

Consulta la [licencia MIT](LICENSE) y los avisos de las dependencias.
