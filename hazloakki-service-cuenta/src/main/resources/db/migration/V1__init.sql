CREATE TABLE cuenta (
  id VARCHAR(255) PRIMARY KEY,
  nombre varchar(255) DEFAULT NULL,
  apellidos varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  nombre_usuario varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  telefono varchar(255) DEFAULT NULL,
  fecha_nacimiento varchar(255) DEFAULT NULL,
  sexo varchar(255) DEFAULT NULL,
  tipo_usuario varchar(255) DEFAULT NULL,
  terminos_condiciones varchar(255) DEFAULT NULL,
  estatus  BOOLEAN NOT NULL DEFAULT FALSE,
  fecha varchar(255) DEFAULT NULL  
);
