package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.Promocion;
import clases.SugerirProducto;
import clases.Absoluta;
import clases.AxB;
import clases.Porcentual;
import clases.TipoAtraccion;
import clases.Usuario;

public class TestSugerirProducto {

	Usuario eowyn, gandalf, sam, galadriel;
	List<Usuario> usuarios;
	Atraccion moria, minasTirith, laComarca, mordor, abismoDeHelm, lothlorein, erebor, bosqueNegro, esgaroth;
	List<Atraccion> atracciones;
	List<String> atraccionesPrimera, atraccionesSegunda, atraccionesTercera, atraccionesCuarta, atraccionesQuinta;
	ArrayList<String> primeras, segundas, terceras, cuartas, quintas;
	Porcentual primera, cuarta;
	Absoluta tercera;
	AxB segunda, quinta;
	List<Promocion> promociones;
	SugerirProducto sistema;

	@Before
	public void setUp() throws Exception {
		usuarios = new ArrayList<Usuario>();
		eowyn = new Usuario(1, "Eowyn", 80, 100, TipoAtraccion.AVENTURA);
		gandalf = new Usuario(2, "Gandalf", 5, 100, TipoAtraccion.PAISAJE);
		sam = new Usuario(3, "Sam", 8, 36, TipoAtraccion.DEGUSTACION);
		galadriel = new Usuario(4, "Galadriel", 6, 120, TipoAtraccion.PAISAJE);
		usuarios.add(eowyn);
		usuarios.add(gandalf);
		usuarios.add(sam);
		usuarios.add(galadriel);
		atracciones = new ArrayList<Atraccion>();
		moria = new Atraccion(1, "Moria", 2, 10, 6, TipoAtraccion.AVENTURA);
		minasTirith = new Atraccion(2, "Minas Tirith", 2.5, 5, 25, TipoAtraccion.PAISAJE);
		laComarca = new Atraccion(3, "La Comarca", 6.5, 3, 150, TipoAtraccion.DEGUSTACION);
		mordor = new Atraccion(4, "Mordor", 3, 25, 4, TipoAtraccion.AVENTURA);
		abismoDeHelm = new Atraccion(5, "Abismo de Heml", 2, 5, 15, TipoAtraccion.PAISAJE);
		lothlorein = new Atraccion(6, "Lothlórein", 1, 35, 30, TipoAtraccion.DEGUSTACION);
		erebor = new Atraccion(7, "Erebor", 3, 12, 32, TipoAtraccion.PAISAJE);
		bosqueNegro = new Atraccion(8, "Bosque Negro", 4, 3, 12, TipoAtraccion.AVENTURA);
		esgaroth = new Atraccion(9, "Esgaroth", 3, 50, 20, TipoAtraccion.DEGUSTACION);
		atracciones.add(moria);
		atracciones.add(minasTirith);
		atracciones.add(laComarca);
		atracciones.add(mordor);
		atracciones.add(abismoDeHelm);
		atracciones.add(lothlorein);
		atracciones.add(erebor);
		atracciones.add(bosqueNegro);
		atracciones.add(esgaroth);
		atraccionesPrimera = new ArrayList<String>();
		atraccionesSegunda = new ArrayList<String>();
		atraccionesTercera = new ArrayList<String>();
		atraccionesCuarta = new ArrayList<String>();
		atraccionesQuinta = new ArrayList<String>();
		atraccionesPrimera.add(bosqueNegro.getId());
		atraccionesPrimera.add(mordor.getId());
		atraccionesSegunda.add(moria.getId());
		atraccionesSegunda.add(mordor.getId());
		atraccionesSegunda.add(bosqueNegro.getId());
		atraccionesTercera.add(lothlorein.getId());
		atraccionesTercera.add(laComarca.getId());
		atraccionesCuarta.add(lothlorein.getId());
		atraccionesCuarta.add(esgaroth.getId());
		atraccionesQuinta.add(minasTirith.getId());
		atraccionesQuinta.add(abismoDeHelm.getId());
		atraccionesQuinta.add(erebor.getId());
		primeras = new ArrayList<String>();
		primeras.add(bosqueNegro.getId());
		primeras.add(mordor.getId());
		segundas = new ArrayList<String>();
		segundas.add(moria.getId());
		segundas.add(mordor.getId());
		segundas.add(bosqueNegro.getId());
		terceras = new ArrayList<String>();
		terceras.add(lothlorein.getId());
		terceras.add(laComarca.getId());
		cuartas = new ArrayList<String>();
		cuartas.add(laComarca.getId());
		cuartas.add(esgaroth.getId());
		quintas = new ArrayList<String>();
		quintas.add(minasTirith.getId());
		quintas.add(abismoDeHelm.getId());
		quintas.add(erebor.getId());
		promociones = new ArrayList<Promocion>();
		primera = new Porcentual(1, "Primera", 7, 22.4, TipoAtraccion.AVENTURA, primeras, 20);
		segunda = new AxB(2, "Segunda", 9, 35, TipoAtraccion.AVENTURA, segundas, bosqueNegro.getId());
		tercera = new Absoluta(3, "Tercera", 32, 36, TipoAtraccion.DEGUSTACION, terceras);
		cuarta = new Porcentual(4, "Cuarta", 7.5, 32, TipoAtraccion.DEGUSTACION, cuartas, 25);
		quinta = new AxB(5, "Quinta", 7.5, 10, TipoAtraccion.PAISAJE, quintas, erebor.getId());
		promociones.add(primera);
		promociones.add(segunda);
		promociones.add(tercera);
		promociones.add(cuarta);
		promociones.add(quinta);
		sistema = new SugerirProducto(usuarios, promociones, atracciones);
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
		atraccionesPrimera = null;
		atraccionesSegunda = null;
		atraccionesTercera = null;
		atraccionesCuarta = null;
		atraccionesQuinta = null;
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
		assertEquals(usuarios, sistema.getUsuarios());
		assertEquals(promociones, sistema.getPromociones());
		assertEquals(atracciones, sistema.getAtracciones());
	}

	@Test
	public void testDeGetAtraccionesDeSuItinerario() {
		eowyn.aceptarSugerencia(bosqueNegro);
		eowyn.aceptarSugerencia(mordor);
		assertTrue(sistema.laVisito(eowyn, atraccionesPrimera.get(0)));
	}

}
