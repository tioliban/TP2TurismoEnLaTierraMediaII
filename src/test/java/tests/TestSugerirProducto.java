package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.Promocion;
import clases.Ofertador;
import clases.Absoluta;
import clases.AxB;
import clases.Base;
import clases.Porcentual;
import clases.TipoAtraccion;
import clases.Usuario;

public class TestSugerirProducto {

	Usuario eowyn, gandalf, sam, galadriel;
	HashMap<String, Usuario> usuarios;
	Atraccion moria, minasTirith, laComarca, mordor, abismoDeHelm, lothlorein, erebor, bosqueNegro, esgaroth;
	HashMap<String, Base> productos;
	HashMap<String, Atraccion> atracciones, primeras, segundas, terceras, cuartas, quintas;
	Porcentual primera, cuarta;
	Absoluta tercera;
	AxB segunda, quinta;
	HashMap<String, Promocion> promociones;
	Ofertador sistema;

	@Before
	public void setUp() throws Exception {
		usuarios = new HashMap<String, Usuario>();
		eowyn = new Usuario(1, "Eowyn", 80, 100, TipoAtraccion.AVENTURA);
		gandalf = new Usuario(2, "Gandalf", 5, 100, TipoAtraccion.PAISAJE);
		sam = new Usuario(3, "Sam", 8, 36, TipoAtraccion.DEGUSTACION);
		galadriel = new Usuario(4, "Galadriel", 6, 120, TipoAtraccion.PAISAJE);
		usuarios.put(Integer.toString(eowyn.getId()), eowyn);
		usuarios.put(Integer.toString(gandalf.getId()), gandalf);
		usuarios.put(Integer.toString(sam.getId()), sam);
		usuarios.put(Integer.toString(galadriel.getId()), galadriel);
		productos = new HashMap<String, Base>();
		moria = new Atraccion(1, "Moria", 2, 10, 6, TipoAtraccion.AVENTURA);
		minasTirith = new Atraccion(2, "Minas Tirith", 2.5, 5, 25, TipoAtraccion.PAISAJE);
		laComarca = new Atraccion(3, "La Comarca", 6.5, 3, 150, TipoAtraccion.DEGUSTACION);
		mordor = new Atraccion(4, "Mordor", 3, 25, 4, TipoAtraccion.AVENTURA);
		abismoDeHelm = new Atraccion(5, "Abismo de Heml", 2, 5, 15, TipoAtraccion.PAISAJE);
		lothlorein = new Atraccion(6, "Lothlórein", 1, 35, 30, TipoAtraccion.DEGUSTACION);
		erebor = new Atraccion(7, "Erebor", 3, 12, 32, TipoAtraccion.PAISAJE);
		bosqueNegro = new Atraccion(8, "Bosque Negro", 4, 3, 12, TipoAtraccion.AVENTURA);
		esgaroth = new Atraccion(9, "Esgaroth", 3, 50, 20, TipoAtraccion.DEGUSTACION);
		atracciones = new HashMap<String, Atraccion>();
		atracciones.put(moria.getId(), moria);
		atracciones.put(minasTirith.getId(), minasTirith);
		atracciones.put(laComarca.getId(), laComarca);
		atracciones.put(mordor.getId(), mordor);
		atracciones.put(abismoDeHelm.getId(), abismoDeHelm);
		atracciones.put(lothlorein.getId(), lothlorein);
		atracciones.put(erebor.getId(), erebor);
		atracciones.put(bosqueNegro.getId(), bosqueNegro);
		atracciones.put(esgaroth.getId(), esgaroth);
		primeras = new HashMap<String, Atraccion>();
		primeras.put(bosqueNegro.getId(), bosqueNegro);
		primeras.put(mordor.getId(), mordor);
		segundas = new HashMap<String, Atraccion>();
		segundas.put(moria.getId(), moria);
		segundas.put(mordor.getId(), mordor);
		segundas.put(bosqueNegro.getId(), bosqueNegro);
		terceras = new HashMap<String, Atraccion>();
		terceras.put(lothlorein.getId(), lothlorein);
		terceras.put(laComarca.getId(), laComarca);
		cuartas = new HashMap<String, Atraccion>();
		cuartas.put(laComarca.getId(), laComarca);
		cuartas.put(esgaroth.getId(), esgaroth);
		quintas = new HashMap<String, Atraccion>();
		quintas.put(minasTirith.getId(), minasTirith);
		quintas.put(abismoDeHelm.getId(), abismoDeHelm);
		quintas.put(erebor.getId(), erebor);
		primera = new Porcentual(1, "Primera", 7, 22.4, TipoAtraccion.AVENTURA, primeras, 20);
		segunda = new AxB(2, "Segunda", 9, 35, TipoAtraccion.AVENTURA, segundas, bosqueNegro.getId());
		tercera = new Absoluta(3, "Tercera", 32, 36, TipoAtraccion.DEGUSTACION, terceras);
		cuarta = new Porcentual(4, "Cuarta", 7.5, 32, TipoAtraccion.DEGUSTACION, cuartas, 25);
		quinta = new AxB(5, "Quinta", 7.5, 10, TipoAtraccion.PAISAJE, quintas, erebor.getId());
		promociones = new HashMap<String, Promocion>();
		promociones.put(primera.getId(), primera);
		promociones.put(segunda.getId(), segunda);
		promociones.put(tercera.getId(), tercera);
		promociones.put(cuarta.getId(), cuarta);
		promociones.put(quinta.getId(), quinta);
		productos = new HashMap<String,Base>();
		productos.putAll(atracciones);
		productos.putAll(promociones);
		sistema = new Ofertador(usuarios, productos);
	}

	@After
	public void tearDown() {
		moria = null;
		mordor = null;
		bosqueNegro = null;
		eowyn = null;
		gandalf = null;
		sam = null;
		galadriel = null;
		moria = null;
		minasTirith = null;
		laComarca = null;
		mordor = null;
		abismoDeHelm = null;
		lothlorein = null;
		erebor = null;
		bosqueNegro = null;
		esgaroth = null;
		usuarios = null;
		atracciones = null;
		primeras = null;
		segundas = null;
		terceras = null;
		cuartas = null;
		quintas = null;
		primera = null;
		segunda = null;
		tercera = null;
		cuarta = null;
		quinta = null;
		promociones = null;
		sistema = null;
	}

	@Test
	public void testDeLosGets() {

	}

	@Test
	public void testDeGetAtraccionesDeSuItinerario() {

	}

}
