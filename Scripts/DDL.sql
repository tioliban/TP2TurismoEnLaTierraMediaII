DROP TABLE IF EXISTS "tipoAtraccion";
CREATE TABLE "tipoAtraccion" (
	"idTipoAtraccion"	INTEGER NOT NULL,
	"nombreTipoAtraccion"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("idTipoAtraccion" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "atracciones";
CREATE TABLE "atracciones" (
	"idAtraccion"	INTEGER NOT NULL,
	"nombreAtraccion"	TEXT NOT NULL UNIQUE,
	"tiempo"	REAL CHECK("tiempo">=0) NOT NULL,
	"costo"	REAL CHECK("costo">=0) NOT NULL,
	"cupo"	INTEGER CHECK("cupo">=0) NOT NULL,
	"idTipoAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("idAtraccion" AUTOINCREMENT),
	FOREIGN KEY("idTipoAtraccion") REFERENCES "tipoAtraccion"(idTipoAtraccion)
);
DROP TABLE IF EXISTS "promociones";
CREATE TABLE "promociones" (
	"idPromocion"	INTEGER NOT NULL,
	"nombrePromocion"	TEXT NOT NULL UNIQUE,
	"idTipoAtraccion"	INTEGER NOT NULL,
	"nombreTipoPromocion"	TEXT NOT NULL,
	PRIMARY KEY("idPromocion" AUTOINCREMENT),
	FOREIGN KEY("idTipoAtraccion") REFERENCES "tipoAtraccion"(idTipoAtraccion)
);
DROP TABLE IF EXISTS "promocionesAtracciones";
CREATE TABLE "promocionesAtracciones" (
	"idPromocionesAtracciones"	INTEGER NOT NULL,
	"idPromocion"	INTEGER NOT NULL,
	"idAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("idPromocionesAtracciones" AUTOINCREMENT),
	UNIQUE ("idPromocion", "idAtraccion"),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion)
);
CREATE TRIGGER borrar_promocionesAtracciones
BEFORE DELETE ON promociones
	BEGIN 
		DELETE FROM promocionesAtracciones WHERE idPromocion = OLD.idPromocion;
	END;
DROP TABLE IF EXISTS "promocionesAxB";
CREATE TABLE "promocionesAxB" (
	"idPromocionAxB"	INTEGER NOT NULL,
	"idPromocion"	INTEGER NOT NULL,
	"idAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("idPromocionAxB" AUTOINCREMENT),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion)
);
CREATE TRIGGER borrar_promocionesAxB
BEFORE DELETE ON promociones
	BEGIN 
		DELETE FROM promocionesAxB WHERE idPromocion = OLD.idPromocion;
	END;
CREATE TRIGGER borrar_promocionAxB
BEFORE UPDATE ON promociones
	BEGIN 
		DELETE FROM promocionesAxB WHERE idPromocion = OLD.idPromocion;
	END;
DROP TABLE IF EXISTS "promocionesAbsolutas";
CREATE TABLE "promocionesAbsolutas" (
	"idPromocionAbsolutas"	INTEGER NOT NULL,
	"idPromocion"	INTEGER NOT NULL,
	"precioFinal"	INTEGER CHECK("precioFinal">=0) NOT NULL,
	PRIMARY KEY("idPromocionAbsolutas" AUTOINCREMENT),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion)
);
CREATE TRIGGER borrar_promocionesAbsolutas
BEFORE DELETE ON promociones
	BEGIN 
		DELETE FROM promocionesAbsolutas WHERE idPromocion = OLD.idPromocion;
	END;
CREATE TRIGGER borrar_promocionAbsoluta
BEFORE UPDATE ON promociones
	BEGIN 
		DELETE FROM promocionesAbsolutas WHERE idPromocion = OLD.idPromocion;
	END;
DROP TABLE IF EXISTS "promocionesPorcentuales";
CREATE TABLE "promocionesPorcentuales" (
	"idPromocionPorcentuales"	INTEGER NOT NULL,
	"idPromocion"	INTEGER NOT NULL,
	"porcentaje"	INTEGER CHECK("porcentaje">=0 AND "porcentaje"<100) NOT NULL,
	PRIMARY KEY("idPromocionPorcentuales" AUTOINCREMENT),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion)
);
CREATE TRIGGER borrar_promocionesPorcentuales
BEFORE DELETE ON promociones
	BEGIN 
		DELETE FROM promocionesPorcentuales WHERE idPromocion = OLD.idPromocion;
	END;
CREATE TRIGGER borrar_promocionPorcentual
BEFORE UPDATE ON promociones
	BEGIN 
		DELETE FROM promocionesPorcentuales WHERE idPromocion = OLD.idPromocion;
	END;
DROP TABLE IF EXISTS "usuarios";
CREATE TABLE "usuarios" (
	"idUsuario"	INTEGER NOT NULL,
	"nombreUsuario"	TEXT NOT NULL UNIQUE,
	"tiempo"	REAL CHECK("tiempo">=0) NOT NULL,
	"presupuesto"	REAL CHECK("presupuesto">=0) NOT NULL,
	"idTipoAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("idUsuario" AUTOINCREMENT),
	FOREIGN KEY("idTipoAtraccion") REFERENCES "tipoAtraccion"(idTipoAtraccion)
);
DROP TABLE IF EXISTS "itinerarioPromociones";
CREATE TABLE "itinerarioPromociones" (
	"idItinerario"	INTEGER NOT NULL,
	"idUsuario"	INTEGER NOT NULL,
	"idPromocion"	INTEGER NOT NULL,
	PRIMARY KEY("idItinerario" AUTOINCREMENT),
	UNIQUE ("idUsuario", "idPromocion"),
	FOREIGN KEY("idUsuario") REFERENCES "usuarios"(idUsuario),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion)
);
CREATE TRIGGER borrar_itinerario_promociones
BEFORE DELETE ON usuarios
	BEGIN 
		DELETE FROM itinerarioPromociones WHERE idUsuario = OLD.idUsuario;
	END;
DROP TABLE IF EXISTS "itinerarioAtracciones";
CREATE TABLE "itinerarioAtracciones" (
	"idItinerario"	INTEGER NOT NULL,
	"idUsuario"	INTEGER NOT NULL,
	"idAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("idItinerario" AUTOINCREMENT),
	UNIQUE ("idUsuario", "idAtraccion"),
	FOREIGN KEY("idUsuario") REFERENCES "usuarios"(idUsuario),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion)
);
CREATE TRIGGER borrar_itinerario_atracciones
BEFORE DELETE ON usuarios
	BEGIN 
		DELETE FROM itinerarioAtracciones WHERE idUsuario = OLD.idUsuario;
	END;
INSERT INTO tipoAtraccion (nombreTipoAtraccion) VALUES
("Aventura"),
("Paisaje"),
("Degustacion");
INSERT INTO usuarios (nombreUsuario, tiempo, presupuesto, idTipoAtraccion) VALUES
("Eowyn", 10, 8, 1),
("Gandalf", 100, 5, 2),
("Sam", 36, 8, 3),
("Galadriel", 120, 6, 2);
INSERT INTO atracciones (nombreAtraccion, tiempo, costo, cupo, idTipoAtraccion) VALUES
("Moria", 2, 10, 6, 1),
("Minas Tirith", 2.5, 5, 25, 2),
("La Comarca", 6.5, 3, 150, 3),
("Mordor", 3, 25, 4, 1),
("Abismo de Helm", 2, 5, 15, 2),
("Lothlorein", 1, 35, 30, 3),
("Erebor", 3, 12, 32, 2),
("Bosque Negro", 4, 3, 15, 1),
("Esgaroth", 3, 20, 50, 3);
INSERT INTO promociones (nombrePromocion, idTipoAtraccion, nombreTipoPromocion) VALUES
("Primera", 1, "Porcentual"),
("Segunda", 1, "AxB"),
("Tercera", 3, "Absoluta"),
("Cuarta", 3, "Porcentual"),
("Quinta", 2, "AxB");
INSERT INTO promocionesAtracciones (idPromocion, idAtraccion) VALUES
(1, 8),
(1, 4),
(2, 1),
(2, 4),
(2, 8),
(3, 6),
(3, 3),
(4, 3),
(4, 9),
(5, 2),
(5, 5),
(5, 7);
INSERT INTO promocionesAxB (idPromocion, idAtraccion) VALUES
(2, 1),
(5, 2);
INSERT INTO promocionesAbsolutas (idPromocion, precioFinal) VALUES
(3, 36);
INSERT INTO promocionesPorcentuales (idPromocion, porcentaje) VALUES
(1, 20),
(4, 25);