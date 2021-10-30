package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.TipoAtraccion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;

public class TestAtraccion {
	HashMap<String, Atraccion> atracciones;
	Atraccion moria, minasTirith, laComarca, mordor, abismoDeHelm, lothlorein, erebor, bosqueNegro, esgaroth;

	@Before
	public void setUp() throws ExcepcionDeBase, ExcepcionDeAtraccion {
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
		atracciones.put(abismoDeHelm.getId(), abismoDeHelm);
		atracciones.put(bosqueNegro.getId(), bosqueNegro);
		atracciones.put(erebor.getId(), erebor);
		atracciones.put(esgaroth.getId(), esgaroth);
		atracciones.put(lothlorein.getId(), lothlorein);
		atracciones.put(laComarca.getId(), laComarca);
		atracciones.put(minasTirith.getId(), minasTirith);
		atracciones.put(mordor.getId(), mordor);
		atracciones.put(moria.getId(), moria);
	}

	@After
	public void tearDown() {
		moria = null;
		minasTirith = null;
		laComarca = null;
		mordor = null;
		abismoDeHelm = null;
		lothlorein = null;
		erebor = null;
		bosqueNegro = null;
		esgaroth = null;
	}

	@Test
	public void testGetId() {
		assertEquals("2.1", moria.getId());
		assertEquals("2.2", minasTirith.getId());
		assertEquals("2.3", laComarca.getId());
		assertEquals("2.4", mordor.getId());
		assertEquals("2.5", abismoDeHelm.getId());
		assertEquals("2.6", lothlorein.getId());
		assertEquals("2.7", erebor.getId());
		assertEquals("2.8", bosqueNegro.getId());
		assertEquals("2.9", esgaroth.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Moria", moria.getNombre());
		assertEquals("Minas Tirith", minasTirith.getNombre());
		assertEquals("La Comarca", laComarca.getNombre());
		assertEquals("Mordor", mordor.getNombre());
		assertEquals("Abismo de Heml", abismoDeHelm.getNombre());
		assertEquals("Lothlórein", lothlorein.getNombre());
		assertEquals("Erebor", erebor.getNombre());
		assertEquals("Bosque Negro", bosqueNegro.getNombre());
		assertEquals("Esgaroth", esgaroth.getNombre());
	}

	@Test
	public void testGetTiempo() {
		assertEquals(2, moria.getTiempo(), 0);
		assertEquals(2.5, minasTirith.getTiempo(), 0);
		assertEquals(6.5, laComarca.getTiempo(), 0);
		assertEquals(3, mordor.getTiempo(), 0);
		assertEquals(2, abismoDeHelm.getTiempo(), 0);
		assertEquals(1, lothlorein.getTiempo(), 0);
		assertEquals(3, erebor.getTiempo(), 0);
		assertEquals(4, bosqueNegro.getTiempo(), 0);
		assertEquals(3, esgaroth.getTiempo(), 0);
	}

	@Test
	public void testGetCosto() {
		assertEquals(10, moria.getCosto(), 0);
		assertEquals(5, minasTirith.getCosto(), 0);
		assertEquals(3, laComarca.getCosto(), 0);
		assertEquals(25, mordor.getCosto(), 0);
		assertEquals(5, abismoDeHelm.getCosto(), 0);
		assertEquals(35, lothlorein.getCosto(), 0);
		assertEquals(12, erebor.getCosto(), 0);
		assertEquals(3, bosqueNegro.getCosto(), 0);
		assertEquals(50, esgaroth.getCosto(), 0);
	}

	@Test
	public void testGetPreferencia() {
		assertEquals(TipoAtraccion.AVENTURA, moria.getTipoAtraccion());
		assertEquals(TipoAtraccion.PAISAJE, minasTirith.getTipoAtraccion());
		assertEquals(TipoAtraccion.DEGUSTACION, laComarca.getTipoAtraccion());
		assertEquals(TipoAtraccion.AVENTURA, mordor.getTipoAtraccion());
		assertEquals(TipoAtraccion.PAISAJE, abismoDeHelm.getTipoAtraccion());
		assertEquals(TipoAtraccion.DEGUSTACION, lothlorein.getTipoAtraccion());
		assertEquals(TipoAtraccion.PAISAJE, erebor.getTipoAtraccion());
		assertEquals(TipoAtraccion.AVENTURA, bosqueNegro.getTipoAtraccion());
		assertEquals(TipoAtraccion.DEGUSTACION, esgaroth.getTipoAtraccion());
	}

	@Test
	public void testGetCupo() {
		assertEquals(6, moria.getCupo(), 0);
		assertEquals(25, minasTirith.getCupo(), 0);
		assertEquals(150, laComarca.getCupo(), 0);
		assertEquals(4, mordor.getCupo(), 0);
		assertEquals(15, abismoDeHelm.getCupo(), 0);
		assertEquals(30, lothlorein.getCupo(), 0);
		assertEquals(32, erebor.getCupo(), 0);
		assertEquals(12, bosqueNegro.getCupo(), 0);
		assertEquals(20, esgaroth.getCupo(), 0);
	}

	@Test
	public void testDeSubirAtraccion() {
		moria.subirAtraccion();
		minasTirith.subirAtraccion();
		laComarca.subirAtraccion();
		mordor.subirAtraccion();
		abismoDeHelm.subirAtraccion();
		lothlorein.subirAtraccion();
		erebor.subirAtraccion();
		bosqueNegro.subirAtraccion();
		esgaroth.subirAtraccion();

		assertEquals(5, moria.getCupo(), 0);
		assertEquals(24, minasTirith.getCupo(), 0);
		assertEquals(149, laComarca.getCupo(), 0);
		assertEquals(3, mordor.getCupo(), 0);
		assertEquals(14, abismoDeHelm.getCupo(), 0);
		assertEquals(29, lothlorein.getCupo(), 0);
		assertEquals(31, erebor.getCupo(), 0);
		assertEquals(11, bosqueNegro.getCupo(), 0);
		assertEquals(19, esgaroth.getCupo(), 0);
	}

	@Test
	public void testDeTieneCupo() {
		moria.subirAtraccion();
		minasTirith.subirAtraccion();
		laComarca.subirAtraccion();
		mordor.subirAtraccion();
		abismoDeHelm.subirAtraccion();
		lothlorein.subirAtraccion();
		erebor.subirAtraccion();
		bosqueNegro.subirAtraccion();
		esgaroth.subirAtraccion();

		assertTrue(moria.tieneCupo());
		assertTrue(minasTirith.tieneCupo());
		assertTrue(laComarca.tieneCupo());
		assertTrue(mordor.tieneCupo());
		assertTrue(abismoDeHelm.tieneCupo());
		assertTrue(lothlorein.tieneCupo());
		assertTrue(erebor.tieneCupo());
		assertTrue(bosqueNegro.tieneCupo());
		assertTrue(esgaroth.tieneCupo());
	}

	@Test
	public void testDeGetAtracciones() {
		assertTrue(moria.getAtracciones().containsKey(moria.getId()));
	}

	@Test
	public void testContainsKey() {
		assertTrue(atracciones.containsKey(moria.getId()));
		assertTrue(atracciones.containsKey(minasTirith.getId()));
		assertTrue(atracciones.containsKey(laComarca.getId()));
		assertTrue(atracciones.containsKey(mordor.getId()));
		assertTrue(atracciones.containsKey(abismoDeHelm.getId()));
		assertTrue(atracciones.containsKey(lothlorein.getId()));
		assertTrue(atracciones.containsKey(erebor.getId()));
		assertTrue(atracciones.containsKey(bosqueNegro.getId()));
		assertTrue(atracciones.containsKey(esgaroth.getId()));
	}

	@Test
	public void testContainsValue() {
		assertTrue(atracciones.containsValue(moria));
		assertTrue(atracciones.containsValue(minasTirith));
		assertTrue(atracciones.containsValue(laComarca));
		assertTrue(atracciones.containsValue(mordor));
		assertTrue(atracciones.containsValue(abismoDeHelm));
		assertTrue(atracciones.containsValue(lothlorein));
		assertTrue(atracciones.containsValue(erebor));
		assertTrue(atracciones.containsValue(bosqueNegro));
		assertTrue(atracciones.containsValue(esgaroth));
	}

	@Test
	public void testEqualsAtraccion() {
		assertTrue(moria.equals(moria));
		assertTrue(minasTirith.equals(minasTirith));
		assertTrue(laComarca.equals(laComarca));
		assertTrue(mordor.equals(mordor));
		assertTrue(abismoDeHelm.equals(abismoDeHelm));
		assertTrue(lothlorein.equals(lothlorein));
		assertTrue(erebor.equals(erebor));
		assertTrue(bosqueNegro.equals(bosqueNegro));
		assertTrue(esgaroth.equals(esgaroth));
	}

	@Test
	public void testEqualsId() {
		assertTrue(moria.equals(moria.getId()));
		assertTrue(minasTirith.equals(minasTirith.getId()));
		assertTrue(laComarca.equals(laComarca.getId()));
		assertTrue(mordor.equals(mordor.getId()));
		assertTrue(abismoDeHelm.equals(abismoDeHelm.getId()));
		assertTrue(lothlorein.equals(lothlorein.getId()));
		assertTrue(erebor.equals(erebor.getId()));
		assertTrue(bosqueNegro.equals(bosqueNegro.getId()));
		assertTrue(esgaroth.equals(esgaroth.getId()));
	}

	@Test
	public void testGetConId() {
		assertEquals(moria, atracciones.get(moria.getId()));
		assertEquals(minasTirith, atracciones.get(minasTirith.getId()));
		assertEquals(laComarca, atracciones.get(laComarca.getId()));
		assertEquals(mordor, atracciones.get(mordor.getId()));
		assertEquals(abismoDeHelm, atracciones.get(abismoDeHelm.getId()));
		assertEquals(lothlorein, atracciones.get(lothlorein.getId()));
		assertEquals(erebor, atracciones.get(erebor.getId()));
		assertEquals(bosqueNegro, atracciones.get(bosqueNegro.getId()));
		assertEquals(esgaroth, atracciones.get(esgaroth.getId()));
	}
}
