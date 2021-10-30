package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.Absoluta;
import clases.TipoAtraccion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class TestPromocionAbsoluta {

	Absoluta absoluta;
	HashMap<String, Atraccion> nombres = new HashMap<String, Atraccion>();
	HashMap<String, Atraccion> atracciones = new HashMap<String, Atraccion>();
	Atraccion moria, minasTirith, laComarca, mordor, abismoDeHelm, lothlorein, erebor, bosqueNegro, esgaroth;

	@Before
	public void setUp() throws ExcepcionDeBase, ExcepcionDeAtraccion, ExcepcionDePromocion {
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

		nombres.put(lothlorein.getId(), lothlorein);
		nombres.put(laComarca.getId(), laComarca);
		absoluta = new Absoluta(1, "Tercera", 7.5, 36, TipoAtraccion.DEGUSTACION, nombres);
	}

	@Test
	public void testGetId() {
		assertEquals("1.1", absoluta.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Tercera", absoluta.getNombre());
	}

	@Test
	public void testGetTiempo() {
		assertEquals(7.5, absoluta.getTiempo(), 0);
	}

	@Test
	public void testGetCosto() {
		assertEquals(36.0, absoluta.getCosto(), 0);
	}

	@Test
	public void testGetPreferencia() {
		assertEquals(TipoAtraccion.DEGUSTACION, absoluta.getTipoAtraccion());
	}

	@Test
	public void testGetCupo() {
		assertTrue(absoluta.tieneCupo());
	}

	@Test
	public void testDeGetAtracciones() {
		for (String ids : absoluta.getAtracciones().keySet()) {
			assertTrue(atracciones.containsKey(absoluta.getAtracciones().get(ids).getId()));
			assertTrue(atracciones.containsValue(absoluta.getAtracciones().get(ids)));
		}
	}

	@Test
	public void testContainsKey() {
		assertTrue(absoluta.getAtracciones().containsKey(lothlorein.getId()));
		assertTrue(absoluta.getAtracciones().containsKey(laComarca.getId()));
	}

	@Test
	public void testContainsValue() {
		assertTrue(absoluta.getAtracciones().containsValue(lothlorein));
		assertTrue(absoluta.getAtracciones().containsValue(laComarca));
	}
}
