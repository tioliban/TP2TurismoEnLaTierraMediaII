package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.Porcentual;
import clases.TipoAtraccion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class TestPromocionPorcentual {

	Porcentual promo;
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
		atracciones.put(abismoDeHelm.getId(), abismoDeHelm);
		atracciones.put(bosqueNegro.getId(), bosqueNegro);
		atracciones.put(erebor.getId(), erebor);
		atracciones.put(esgaroth.getId(), esgaroth);
		atracciones.put(lothlorein.getId(), lothlorein);
		atracciones.put(laComarca.getId(), laComarca);
		atracciones.put(minasTirith.getId(), minasTirith);
		atracciones.put(mordor.getId(), mordor);
		atracciones.put(moria.getId(), moria);
		nombres.put(moria.getId(), moria);
		nombres.put(mordor.getId(), mordor);
		promo = new Porcentual(1, "Segunda", 5, 22.75, TipoAtraccion.AVENTURA, nombres, 35);
	}

	@Test
	public void testGetId() {
		assertEquals("1.1", promo.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Segunda", promo.getNombre());
	}

	@Test
	public void testGetTiempo() {
		assertEquals(5, promo.getTiempo(), 0);
	}

	@Test
	public void testGetCosto() {
		assertEquals(22.75, promo.getCosto(), 0);
	}

	@Test
	public void testGetPreferencia() {
		assertEquals(TipoAtraccion.AVENTURA, promo.getTipoAtraccion());
	}

	@Test
	public void testDeGetPorcentajeDeDescuento() {
		assertEquals(35, promo.getPorcentajeDescuento(), 0);
	}

	@Test
	public void testGetCupo() {
		assertTrue(promo.tieneCupo());
	}

	@Test
	public void testDeGetAtracciones() {
		for (String ids : promo.getAtracciones().keySet()) {
			assertTrue(atracciones.containsKey(promo.getAtracciones().get(ids).getId()));
			assertTrue(atracciones.containsValue(promo.getAtracciones().get(ids)));
		}
	}

	@Test
	public void testContainsKey() {
		assertTrue(promo.getAtracciones().containsKey(moria.getId()));
		assertTrue(promo.getAtracciones().containsKey(mordor.getId()));
	}

	@Test
	public void testContainsValue() {
		assertTrue(promo.getAtracciones().containsValue(moria));
		assertTrue(promo.getAtracciones().containsValue(mordor));
	}
}
