CREATE TABLE "tipoAtraccion" (
	"nombreTipo"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("nombreTipo")
);
CREATE TABLE "atracciones" (
	"nombreAtraccion"	TEXT NOT NULL UNIQUE,
	"tiempo"	REAL NOT NULL,
	"costo"	REAL NOT NULL,
	"cupo"	INTEGER NOT NULL,
	"nombreTipo"	TEXT NOT NULL,
	PRIMARY KEY("nombreAtraccion"),
	FOREIGN KEY("nombreTIpo") REFERENCES "tipoAtraccion"(nombreTipo)
);
CREATE TABLE "promociones" (
	"nombrePromocion" TEXT NOT NULL UNIQUE,
	"tipo" TEXT NOT NULL,
	"precioFinal" REAL DEFAULT -1,
	"descuento" REAL DEFAULT -1,
	PRIMARY KEY("nombrePromocion")
);
CREATE TABLE "promocionesAtracciones" (
	"id"	INTEGER NOT NULL UNIQUE,
	"nombrePromocion"	TEXT NOT NULL,
	"nombreAtraccion"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	UNIQUE ("nombrePromocion","nombreAtraccion"),
	FOREIGN KEY("nombrePromocion") REFERENCES "promociones"(nombrePromocion),
	FOREIGN KEY("nombreAtraccion") REFERENCES "atracciones"(nombreAtraccion)
);
CREATE TABLE "usuarios" (
	"nombreUsuario"	TEXT NOT NULL UNIQUE,
	"tiempo"	REAL NOT NULL,
	"presupuesto"	REAL NOT NULL,
	"nombreTipo"	INTEGER NOT NULL,
	PRIMARY KEY("nombreUsuario"),
	FOREIGN KEY("nombreTipo") REFERENCES "tipoAtraccion"(nombreTipo)
);
CREATE TABLE "itinerarios" (
	"idItinerario"	INTEGER NOT NULL UNIQUE,
	"nombreUsuario"	TEXT NOT NULL,
	"nombrePromocion"	TEXT,
	"nombreAtraccion"	TEXT,
	PRIMARY KEY("idItinerario" AUTOINCREMENT),
	FOREIGN KEY("nombreUsuario") REFERENCES "usuarios"(nombreUsuario),
	FOREIGN KEY("nombrePromocion") REFERENCES "promociones"(nombrePromocion),
	FOREIGN KEY("nombreAtraccion") REFERENCES "atracciones"(nombreAtraccion)
);
INSERT into tipoAtraccion values ("Aventura");
INSERT into tipoAtraccion values ("Paisaje");
INSERT into tipoAtraccion values ("Degustacion");
INSERT into usuarios values ("Eowyn", 8, 10, "Aventura");
INSERT into usuarios values ("Gandalf", 5, 100, "Paisaje");
INSERT into usuarios values ("Sam", 8, 36, "Degustacion");
INSERT into usuarios values ("Galadriel", 6, 120, "Paisaje");
INSERT into atracciones values ("Moria", 2, 10, 6, "Aventura");
INSERT into atracciones values ("Minas Tirith", 2.5, 5, 25, "Paisaje");
INSERT into atracciones values ("La Comarca", 6.5, 3, 150, "Degustacion");
INSERT into atracciones values ("Mordor", 3, 25, 4, "Aventura");
INSERT into atracciones values ("Abismo de Helm", 2, 5, 15, "Paisaje");
INSERT into atracciones values ("Lothlorein", 1, 35, 30, "Degustacion");
INSERT into atracciones values ("Erebor", 3, 12, 32, "Paisaje");
INSERT into atracciones values ("Bosque Negro", 4, 3, 15, "Aventura");
INSERT into atracciones values ("Esgaroth", 3, 20, 50, "Degustacion");
INSERT into promociones values ("Primera", "Porcentual", -1, 2);
INSERT into promociones values ("Segunda", "AxB", -1, -1);
INSERT into promociones values ("Tercera", "Absoluta", 36, -1);
INSERT into promociones values ("Cuarta", "Porcentual", -1, 25);
INSERT into promociones values ("Quinta", "AxB", -1, -1);
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Primera", "Bosque Negro");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Primera", "Mordor");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Segunda", "Moria");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Segunda", "Mordor");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Segunda", "Bosque Negro");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Tercera", "Lothlorein");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Tercera", "La Comarca");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Cuarta", "La Comarca");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Cuarta", "Esgaroth");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Quinta", "Minas Tirith");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Quinta", "Abismo de Helm");
INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion) values ("Quinta", "Erebor");