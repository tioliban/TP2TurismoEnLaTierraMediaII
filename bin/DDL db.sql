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