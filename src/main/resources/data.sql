 /*-------------------------------------------------------------------------------------------*/
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
  idusuario INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  PRIMARY KEY (idusuario));

INSERT INTO usuario (nombre) VALUES
('Ricardo'),
('Juan'),
('Pedro'),
('Jose'),
('Carlos');

/*-------------------------------------------------------------------------------------------*/

DROP TABLE IF EXISTS tarea;
CREATE TABLE tarea (
  idtarea INT NOT NULL AUTO_INCREMENT,
  idusuarioPK INT NOT NULL,
  estado INT NOT NULL,
  fechaejecucion DATE NULL,
  fechacierre DATE NULL,
  PRIMARY KEY (idtarea));

ALTER TABLE tarea 
ADD CONSTRAINT idusuarioPK
  FOREIGN KEY (idusuarioPK)
  REFERENCES usuario (idusuario);

INSERT INTO tarea (idusuarioPK, estado, fechaejecucion, fechacierre) VALUES 
(1, 1, DATE'2023-02-01', DATE'2023-02-05'),
(2, 2, DATE'2023-02-02', NULL),
(3, 1, DATE'2023-02-04', DATE'2023-02-16'),
(4, 2, DATE'2023-02-28', NULL);
/*-------------------------------------------------------------------------------------------*/