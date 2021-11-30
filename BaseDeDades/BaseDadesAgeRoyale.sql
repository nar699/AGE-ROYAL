CREATE SCHEMA ageroyale;
use ageroyale;

CREATE TABLE Usuari (
  nom_usuari varchar(255) ,
  correu varchar(255) ,
  contrasenya varchar(255),
  wins INTEGER,
  partides INTEGER,
  PRIMARY KEY (nom_usuari)
); 
CREATE TABLE Historial (
  url_partida varchar(255) ,
  data datetime ,
  guanyador boolean ,
  nom_usuari varchar(255) ,
  PRIMARY KEY (url_partida),
  FOREIGN KEY (nom_usuari) REFERENCES usuari (nom_usuari)
);
