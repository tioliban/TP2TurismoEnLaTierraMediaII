CREATE TABLE "tipoAtraccion" (
	"idTipo"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("idTipo" AUTOINCREMENT)
);
CREATE TABLE "atracciones" (
	"idAtraccion"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL,
	"tiempo"	REAL NOT NULL,
	"costo"	REAL NOT NULL,
	"cupo"	INTEGER NOT NULL,
	"idTipo"	INTEGER NOT NULL,
	PRIMARY KEY("idAtraccion" AUTOINCREMENT),
	FOREIGN KEY("idTipo") REFERENCES "tipoAtraccion"(idTipo)
);
CREATE TABLE "promociones" (
	"idPromocion"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL,
	"tipo"	TEXT NOT NULL,
	"precio" REAL NOT NULL DEFAULT -1,
	"descuento" REAL NOT NULL DEFAULT -1,
	"idAtraccion" INTEGER,
	PRIMARY KEY("idPromocion" AUTOINCREMENT),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion)
);
CREATE TABLE "promocionAtraccion" (
	"id"	INTEGER NOT NULL UNIQUE,
	"idPromocion"	INTEGER NOT NULL,
	"idAtraccion"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion)
);
CREATE TABLE "usuarios" (
	"idUsuario"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL,
	"tiempo"	REAL NOT NULL,
	"presupuesto"	REAL NOT NULL,
	"idTipo"	INTEGER NOT NULL,
	PRIMARY KEY("idUsuario" AUTOINCREMENT),
	FOREIGN KEY("idTipo") REFERENCES "tipoAtraccion"(idTipo)
);
CREATE TABLE "itinerarios" (
	"idItinerario"	INTEGER NOT NULL UNIQUE,
	"idUsuario"	INTEGER NOT NULL,
	"idPromocion"	INTEGER,
	"idAtraccion"	INTEGER,
	PRIMARY KEY("idItinerario" AUTOINCREMENT),
	FOREIGN KEY("idUsuario") REFERENCES "usuarios"(idUsuario),
	FOREIGN KEY("idPromocion") REFERENCES "promociones"(idPromocion),
	FOREIGN KEY("idAtraccion") REFERENCES "atracciones"(idAtraccion)
);
INSERT into tipoAtraccion (nombre) values ("Aventura");
INSERT into tipoAtraccion (nombre) values ("Paisaje");
INSERT into tipoAtraccion (nombre) values ("Degustacion");
INSERT into usuarios (nombre, tiempo, presupuesto, idTipo) values ("Eowyn", 8, 10, 1);
INSERT into usuarios (nombre, tiempo, presupuesto, idTipo) values ("Gandalf", 5, 100, 2);
INSERT into usuarios (nombre, tiempo, presupuesto, idTipo) values ("Sam", 8, 36, 3);
INSERT into usuarios (nombre, tiempo, presupuesto, idTipo) values ("Galadriel", 6, 120, 2);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Moria", 2, 10, 6, 1);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Minas Tirith", 2.5, 5, 25, 2);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("La Comarca", 6.5, 3, 150, 3);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Mordor", 3, 25, 4, 1);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Abismo de Helm", 2, 5, 15, 2);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Lothlorein", 1, 35, 30, 3);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Erebor", 3, 12, 32, 2);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Bosque Negro", 4, 3, 15, 1);
INSERT into atracciones (nombre, tiempo, costo, cupo, idTipo) values ("Esgaroth", 3, 20, 50, 3);
INSERT into promociones (nombre, tipo, precio, descuento, idAtraccion) values ("Primera", "Porcentual", -1, 2, NULL);
INSERT into promociones (nombre, tipo, precio, descuento, idAtraccion) values ("Segunda", "AxB", -1, -1, 1);
INSERT into promociones (nombre, tipo, precio, descuento, idAtraccion) values ("Tercera", "Absoluta", 36, -1, NULL);
INSERT into promociones (nombre, tipo, precio, descuento, idAtraccion) values ("Cuarta", "Porcentual", -1, 25, NULL);
INSERT into promociones (nombre, tipo, precio, descuento, idAtraccion) values ("Quinta", "AxB", -1, -1, 2);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (1, 8);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (1, 4);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (2, 1);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (2, 4);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (2, 8);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (3, 6);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (3, 3);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (4, 3);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (4, 9);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (5, 2);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (5, 5);
INSERT into promocionAtraccion (idPromocion, idAtraccion) values (5, 7);