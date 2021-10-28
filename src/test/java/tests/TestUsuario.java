package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.AxB;
import clases.TipoAtraccion;
import clases.Usuario;

import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;
import excepciones.ExcepcionDeUsuario;

public class TestUsuario {
	List<Atraccion> atracciones;
	ArrayList<String> nombres;
	Atraccion moria, mordor, bosqueNegro, minasTirith, laComarca, abismoDeHelm, lothlorein, erebor, esgaroth;
	AxB promo;
	Usuario eowyn, gandalf, sam, galadriel;
	Usuario nombreInvalido, tiempoInvalido, presupuestoInvalido;

	@Before
	public void setUp() throws ExcepcionDeUsuario, ExcepcionDeBase, ExcepcionDeAtraccion, ExcepcionDePromocion {
		eowyn = new Usuario(1, "Eowyn", 8, 11, TipoAtraccion.AVENTURA);
		gandalf = new Usuario(2, "Gandalf", 5, 100, TipoAtraccion.PAISAJE);
		sam = new Usuario(3, "Sam", 8, 36, TipoAtraccion.DEGUSTACION);
		galadriel = new Usuario(4, "Galadriel", 6, 120, TipoAtraccion.PAISAJE);
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
		atracciones.add(abismoDeHelm);
		atracciones.add(bosqueNegro);
		atracciones.add(erebor);
		atracciones.add(esgaroth);
		atracciones.add(lothlorein);
		atracciones.add(laComarca);
		atracciones.add(minasTirith);
		atracciones.add(mordor);
		atracciones.add(moria);
		atracciones.add(moria);
		atracciones.add(mordor);
		atracciones.add(bosqueNegro);
		nombres = new ArrayList<String>();
		nombres.add(moria.getId());
		nombres.add(mordor.getId());
		nombres.add(bosqueNegro.getId());
		promo = new AxB(1, "Segunda", 9, 28, TipoAtraccion.AVENTURA, nombres, bosqueNegro.getId());
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
		nombreInvalido = null;
		tiempoInvalido = null;
		presupuestoInvalido = null;
	}

	@Test
	public void testGetNombre() {
		assertEquals("Eowyn", eowyn.getNombre());
		assertEquals("Gandalf", gandalf.getNombre());
		assertEquals("Sam", sam.getNombre());
		assertEquals("Galadriel", galadriel.getNombre());
	}

	@Test
	public void testGetTiempo() {
		assertSame(8, eowyn.getTiempo());
		assertSame(5, gandalf.getTiempo());
		assertSame(8, sam.getTiempo());
		assertSame(6, galadriel.getTiempo());
	}

	@Test
	public void testGetPresupuesto() {
		assertSame(11, eowyn.getPresupuesto());
		assertSame(100, gandalf.getPresupuesto());
		assertSame(36, sam.getPresupuesto());
		assertSame(120, galadriel.getPresupuesto());
	}

	@Test
	public void testGetPreferencia() {
		assertEquals(TipoAtraccion.AVENTURA, eowyn.getPreferencia());
		assertEquals(TipoAtraccion.PAISAJE, gandalf.getPreferencia());
		assertEquals(TipoAtraccion.DEGUSTACION, sam.getPreferencia());
		assertEquals(TipoAtraccion.PAISAJE, galadriel.getPreferencia());
	}

	@Test
	public void testDeSugerenciaAceptada() {
		assertTrue(eowyn.aceptarSugerencia(moria, true));
		assertEquals(6.0, eowyn.getTiempo(), 0);
		assertEquals(1.0, eowyn.getPresupuesto(), 0);
		assertFalse(eowyn.getItinerario().isEmpty());
		assertTrue((eowyn.getItinerario().contains(moria)));
	}

	@Test
	public void testDeSugerenciaRechazada() {
		assertFalse(eowyn.aceptarSugerencia(moria, false));
		assertEquals(8, eowyn.getTiempo(), 0);
		assertEquals(10, eowyn.getPresupuesto(), 0);
		assertTrue(eowyn.getItinerario().isEmpty());
	}

	@Test
	public void testDeToString() {
		String textoEsperado = "Eowyn, con un tiempo disponible de 8.0, un presupuesto de 10.0 monedas de oro"
				+ " y una preferencia para las atracciones de tipo AVENTURA";
		assertEquals(textoEsperado, eowyn.toString());
	}
}
