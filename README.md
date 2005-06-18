# Transmilleno
[English](#english) · [Español](#español)

## English

Web application for registering buses, stations and routes in an administration panel

> [!WARNING]
> **Status: Archived.** Dated editions with local verification steps.

### Overview

Web application for registering buses, stations and routes in an administration panel.

### Features

- Maintain buses, stations and routes through the password-protected PHP administration screens.

### Requirements

Python 3.12.4 runs the build script. The web build uses Docker with PHP 5.6 and Node.js for syntax checks. The old administration demonstration uses a disposable MySQL 5.7 service.

### Getting started

Run `python demo/compilar.py --output <new-folder>` from this checkout. The script builds the editions present in the tree.

### Usage

Open the PHP administration form in the local disposable web server.

### Project structure

Folders in this edition include `demo/`, `transmilenio/`.

### Verification

Built on 20 November 2026 on Windows 10.0.26300 from the source edition dated 2005-06-18. The archived form opens but the original 2005 session is lost before the administration panel.

### History

The source edition keeps its original date of 2005-06-18.

### Support

Reproduce the issue locally and include the edition and its relevant log.

### Contributing

Keep original notices and use fictional examples in any test.

### License

See the [MIT license](LICENSE) and the notices on the dependencies.

---

## Español

Aplicación web para registrar buses, estaciones y recorridos en un panel de administración

> [!WARNING]
> **Estado: Archivado.** Ediciones por fecha con pasos de prueba local.

### Descripción general

Aplicación web para registrar buses, estaciones y recorridos en un panel de administración.

### Funciones

- Gestiona buses, estaciones y rutas desde las pantallas de administración PHP con ingreso de usuario.

### Requisitos

Python 3.12.4 ejecuta el script de compilación. La compilación web comprueba sintaxis con Docker, PHP 5.6 y Node.js. La demostración de administración usa MySQL 5.7 desechable.

### Cómo empezar

Ejecuta `python demo/compilar.py --output <new-folder>` desde esta copia. El script compila las ediciones que están en el árbol.

### Uso

Abre el formulario de administración PHP en el servidor local desechable.

### Estructura del proyecto

Las carpetas de esta edición incluyen `demo/`, `transmilenio/`.

### Verificación

Compilado el 20 de noviembre de 2026 en Windows 10.0.26300 desde la edición del 2005-06-18. Abre el formulario archivado. La sesión de 2005 se pierde antes de entrar al panel.

### Historial

La edición conserva su fecha original del 2005-06-18.

### Soporte

Reproduce el problema en local e indica la edición y el registro correspondiente.

### Contribuir

Conserva los avisos originales y usa ejemplos ficticios en las pruebas.

### Licencia

Consulta la [licencia MIT](LICENSE) y los avisos de las dependencias.
