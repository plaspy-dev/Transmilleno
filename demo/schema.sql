SET SESSION sql_mode='';
SET SESSION default_storage_engine=MyISAM;

CREATE TABLE AYUDA (
  codigo float NOT NULL auto_increment,
  placa varchar(30) NOT NULL default '',
  dueno varchar(30) NOT NULL default '',
  fechaingreso date NOT NULL default '0000-00-00',
  modelo float NOT NULL default '0',
  caracteristica char(3) NOT NULL default '',
  parqueadero float NOT NULL default '0',
  repuesto float default NULL,
  conductorayuda float NOT NULL default '0',
  ayudaturno float NOT NULL default '0',
  PRIMARY KEY  (codigo),
  FOREIGN KEY (parqueadero) REFERENCES PARQUEADERO (codigo),
  FOREIGN KEY (repuesto) REFERENCES REPUESTO (codigo),
  FOREIGN KEY (conductorayuda) REFERENCES CONDUCTORAYUDA (codigo),
  FOREIGN KEY (ayudaturno) REFERENCES AYUDATURNO (codigo)
);

CREATE TABLE AYUDATURNO (
  codigo float NOT NULL default '0',
  ayuda float NOT NULL default '0',
  turno float NOT NULL default '0',
  PRIMARY KEY  (codigo),
  FOREIGN KEY (ayuda) REFERENCES AYUDA (codigo),
  FOREIGN KEY (turno) REFERENCES TURNO (codigo)
);

CREATE TABLE BUS (
  codigo float NOT NULL auto_increment,
  placa varchar(30) NOT NULL default '',
  dueno varchar(30) NOT NULL default '',
  npasageros float NOT NULL default '0',
  fechaingreso date NOT NULL default '0000-00-00',
  modelo float NOT NULL default '0',
  marcamotor varchar(30) NOT NULL default '',
  marcachasis varchar(30) NOT NULL default '',
  parqueadero float NOT NULL default '0',
  PRIMARY KEY  (codigo),
  FOREIGN KEY (parqueadero) REFERENCES PARQUEADERO (codigo)
);

CREATE TABLE BUSCONDUCTORRUTA (
  codigo float NOT NULL AUTO_INCREMENT,
  bus float NOT NULL default '0',
  conductor float NOT NULL default '0',
  ruta float NOT NULL default '0',
  fecha datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (codigo,bus,conductor,ruta),
  FOREIGN KEY (conductor) REFERENCES CONDUCTOR (codigo),
  FOREIGN KEY (ruta) REFERENCES RUTA (codigo),
  FOREIGN KEY (bus) REFERENCES BUS (codigo)
);

CREATE TABLE BUSCONDUCTORTURNO (
  codigo float NOT NULL AUTO_INCREMENT,
  bus float NOT NULL default '0',
  conductor float NOT NULL default '0',
  turno float NOT NULL default '0',
  fecha datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (codigo,bus,conductor,turno),
  FOREIGN KEY (conductor) REFERENCES CONDUCTOR (codigo),
  FOREIGN KEY (turno) REFERENCES TURNO (codigo)
);

CREATE TABLE CONDUCTOR (
  cedula float NOT NULL default '0',
  nombre varchar(30) NOT NULL default '',
  apellido varchar(30) NOT NULL default '',
  npase float NOT NULL default '0',
  ncategoria float NOT NULL default '0',
  tipovehiculo char(1) NOT NULL default '',
  PRIMARY KEY  (cedula)
);

CREATE TABLE CONDUCTORAYUDA (
  codigo float NOT NULL AUTO_INCREMENT,
  conductor float NOT NULL default '0',
  ayuda float NOT NULL default '0',
  turno float NOT NULL default '0',
  PRIMARY KEY  (codigo,ayuda,conductor),
  FOREIGN KEY (conductor) REFERENCES CONDUCTOR (codigo),
  FOREIGN KEY (ayuda) REFERENCES AYUDA (codigo),
  FOREIGN KEY (turno) REFERENCES TURNO (codigo)
);

CREATE TABLE CONDUCTORDINERO (
  codigo float NOT NULL AUTO_INCREMENT,
  conductor float NOT NULL default '0',
  dinero float NOT NULL default '0',
  PRIMARY KEY  (codigo,conductor,dinero),
  FOREIGN KEY (conductor) REFERENCES CONDUCTOR (codigo) ,
  FOREIGN KEY (dinero) REFERENCES DINERO (codigo)
);

CREATE TABLE DINERO (
  codigo float NOT NULL default '0',
  placa varchar(30) NOT NULL default '',
  dueno varchar(30) NOT NULL default '',
  fechaingreso date NOT NULL default '0000-00-00',
  model float NOT NULL default '0',
  capacidaddinero float NOT NULL default '0',
  escolta float NOT NULL default '0',
  parqueadero float NOT NULL default '0',
  PRIMARY KEY  (codigo),
  FOREIGN KEY (parqueadero) REFERENCES PARQUEADERO (codigo)
);

CREATE TABLE DINEROESTACION (
  codigo float NOT NULL default '0',
  dinero float NOT NULL default '0',
  estacion float NOT NULL default '0',
  fecha date NOT NULL default '0000-00-00',
  PRIMARY KEY  (codigo,dinero,estacion),
  FOREIGN KEY (dinero) REFERENCES DINERO (codigo),
  FOREIGN KEY (estacion) REFERENCES ESTACION (codigo)
);

CREATE TABLE ESTACION (
  codigo float NOT NULL default '0',
  nombre varchar(30) NOT NULL default '',
  direccion varchar(30) NOT NULL default '',
  trasbordo float NOT NULL default '0',
  PRIMARY KEY  (codigo)
);

CREATE TABLE HISTORIALBUS (
  codigo float NOT NULL AUTO_INCREMENT,
  acontecimiento varchar(100) NOT NULL default '',
  arreglo float NOT NULL default '0',
  policia float NOT NULL default '0',
  fechaacontecimiento date NOT NULL default '0000-00-00',
  bus float NOT NULL default '0',
  PRIMARY KEY  (codigo),
  FOREIGN KEY (bus) REFERENCES BUS (codigo)
);

CREATE TABLE PARQUEADERO (
  codigo float NOT NULL default '0',
  nombre varchar(30) NOT NULL default '',
  capacidad float NOT NULL default '0',
  taller float NOT NULL default '0',
  bomba float NOT NULL default '0',
  labadero float NOT NULL default '0',
  PRIMARY KEY  (codigo)
);

CREATE TABLE REPUESTO (
  codigo float NOT NULL AUTO_INCREMENT,
  nombre varchar(30) NOT NULL default '',
  precio varchar(15) NOT NULL default '',
  fechacoloacion date default '0000-00-00',
  PRIMARY KEY  (codigo)
);

CREATE TABLE RUTA (
  codigo float NOT NULL default '0',
  observacion varchar(100) NOT NULL default '',
  tiemporecorrido time NOT NULL default '00:00:00',
  PRIMARY KEY  (codigo)
);

CREATE TABLE RUTAESTACION (
  codigo float NOT NULL AUTO_INCREMENT,
  ruta float NOT NULL default '0',
  estacion float NOT NULL default '0',
  observaciones varchar(100) NOT NULL default '',
  PRIMARY KEY  (codigo,estacion,ruta),
  FOREIGN KEY (ruta) REFERENCES RUTA (codigo),
  FOREIGN KEY (estacion) REFERENCES ESTACION (codigo)
);

CREATE TABLE TAQUILLERO (
  codigo float NOT NULL default '0',
  nombre varchar(30) NOT NULL default '',
  apellido varchar(30) NOT NULL default '',
  niveleducativo varchar(30) NOT NULL default '',
  PRIMARY KEY  (codigo)
);

CREATE TABLE TAQUILLEROTURNO (
  codigo float NOT NULL default '0',
  turno float NOT NULL default '0',
  taquillero float NOT NULL default '0',
  fecha date NOT NULL default '0000-00-00',
  PRIMARY KEY  (codigo,taquillero,turno),
  FOREIGN KEY (turno) REFERENCES TURNO (codigo),
  FOREIGN KEY (taquillero) REFERENCES TAQUILLERO (codigo)
);

CREATE TABLE TURNO (
  codigo float NOT NULL default '0',
  horainicio time NOT NULL default '00:00:00',
  horafin time NOT NULL default '00:00:00',
  PRIMARY KEY  (codigo)
);

INSERT INTO ESTACION (codigo,nombre,direccion,trasbordo) VALUES
  (501,'Demo Parada 501','Av. Ficticia 501',0), (502,'Demo Parada 502','Av. Ficticia 502',0);
INSERT INTO RUTA (codigo,observacion,tiemporecorrido) VALUES (501,'Recorrido ficticio D01','00:25:00');
