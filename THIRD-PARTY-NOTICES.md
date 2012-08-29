# External dependencies / Dependencias externas

- Apache Cordova for Android 2.0.0. Use the original Android JAR from the [Cordova 2.0.0 distribution](https://cordova.apache.org/archive/docs/en/2.0.0/guide/getting-started/android/). SHA-256 of the tested archive copy: `832974796d2a6d1d4bdb275ad82a5950c614810d029ad03b502cade40e204ec3`. The Apache License 2.0 applies to Cordova.
- Google AdMob Ads SDK 6.1.0. Use the exact original `GoogleAdMobAdsSdk-6.1.0.jar` from the historical Android Ads SDK archive if you have access. SHA-256 `a03f5ac6baf4df84cf8d0f27d19ca2d4d0a959f2d80076698b4383bc52d93b05`. Google SDK terms apply.
- [MicroEmulator 2.0.4](https://central.sonatype.com/artifact/org.microemu/microemulator/2.0.4). Install `microemulator-2.0.4.jar` externally for building and running the MIDlet. The upstream package carries its own notices.
- For the browser editions, use the corresponding jQuery, jQuery UI and jQuery Mobile assets named in the pages. Retain the upstream licenses. The archived map images, encoded route catalogs and packaged phone distributions remain external while their provenance is reviewed.

Las dependencias se instalan por separado. Conserva sus avisos originales. Para Android y Java ME coloca los JAR con los nombres indicados en la carpeta que recibe `--external`. Las versiones de jQuery aparecen en las referencias de cada página. Los mapas, catálogos codificados y paquetes móviles se mantienen externos mientras se confirma su procedencia.
