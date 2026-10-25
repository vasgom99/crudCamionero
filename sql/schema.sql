-- Esquema de referencia del modelo DB_PAQUETERIA (Transporte).
-- Ejecute solo si necesita crear el esquema local desde el modelo .mwb.
CREATE DATABASE IF NOT EXISTS db_paqueteria
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE db_paqueteria;

CREATE TABLE IF NOT EXISTS Camionero (
  idCamionero INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(60) NOT NULL,
  Telefono VARCHAR(20) NOT NULL,
  Direccion VARCHAR(100) NOT NULL,
  Salario DOUBLE(18,2) NOT NULL,
  Poblacion VARCHAR(45) NOT NULL,
  PRIMARY KEY (idCamionero)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Camion (
  idCamion INT NOT NULL AUTO_INCREMENT,
  NoPlaca VARCHAR(45) NOT NULL,
  Modelo VARCHAR(45) NOT NULL,
  Tipo VARCHAR(45) NOT NULL,
  Potencia INT NOT NULL,
  PRIMARY KEY (idCamion),
  UNIQUE KEY uq_camion_noplaca (NoPlaca)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Ciudad (
  idCiudad INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(45) NOT NULL,
  PRIMARY KEY (idCiudad)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Paquete (
  idPaquete INT NOT NULL AUTO_INCREMENT,
  Descripcion VARCHAR(45) NOT NULL,
  Destinatario VARCHAR(45) NOT NULL,
  DireccionEnvio VARCHAR(45) NOT NULL,
  idCamionero INT NOT NULL,
  idCiudad INT NOT NULL,
  PRIMARY KEY (idPaquete),
  KEY ix_paquete_camionero (idCamionero),
  KEY ix_paquete_ciudad (idCiudad),
  CONSTRAINT fk_paquete_camionero FOREIGN KEY (idCamionero)
    REFERENCES Camionero (idCamionero),
  CONSTRAINT fk_paquete_ciudad FOREIGN KEY (idCiudad)
    REFERENCES Ciudad (idCiudad)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS CamioneroCamion (
  idCamioneroCamion INT NOT NULL AUTO_INCREMENT,
  idCamionero INT NOT NULL,
  idCamion INT NOT NULL,
  PRIMARY KEY (idCamioneroCamion),
  UNIQUE KEY uq_camionero_camion (idCamionero, idCamion),
  KEY ix_camionero_camion_camion (idCamion),
  CONSTRAINT fk_camionerocamion_camionero FOREIGN KEY (idCamionero)
    REFERENCES Camionero (idCamionero),
  CONSTRAINT fk_camionerocamion_camion FOREIGN KEY (idCamion)
    REFERENCES Camion (idCamion)
) ENGINE=InnoDB;
