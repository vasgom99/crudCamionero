-- Datos ficticios para una demostración local. No ejecute sobre una base con datos reales.
USE db_paqueteria;

INSERT INTO Ciudad (idCiudad, Nombre) VALUES
  (1, 'Ciudad de Guatemala'),
  (2, 'Quetzaltenango'),
  (3, 'Escuintla');

INSERT INTO Camionero (idCamionero, Nombre, Telefono, Direccion, Salario, Poblacion) VALUES
  (1, 'Ana López', '5555-0101', 'Zona 1, Ciudad de Guatemala', 4200.00, 'Guatemala'),
  (2, 'Luis García', '5555-0202', 'Zona 3, Quetzaltenango', 3900.00, 'Quetzaltenango');

INSERT INTO Camion (idCamion, NoPlaca, Modelo, Tipo, Potencia) VALUES
  (1, 'P-01ABC', 'Hino 500', 'Carga seca', 260),
  (2, 'P-02DEF', 'Isuzu FTR', 'Refrigerado', 240);

INSERT INTO CamioneroCamion (idCamioneroCamion, idCamionero, idCamion) VALUES
  (1, 1, 1),
  (2, 2, 2);

INSERT INTO Paquete (idPaquete, Descripcion, Destinatario, DireccionEnvio, idCamionero, idCiudad) VALUES
  (1, 'Material escolar', 'Carlos Méndez', 'Zona 4, Ciudad de Guatemala', 1, 1),
  (2, 'Repuestos automotrices', 'María Pérez', 'Zona 1, Quetzaltenango', 2, 2);
