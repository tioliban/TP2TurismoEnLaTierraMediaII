package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Atraccion;
import clases.TipoAtraccion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;

public class TestAtraccion {
	ArrayList<Atraccion>atracciones;
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
		atracciones = new ArrayList<Atraccion>();
		atracciones.add(abismoDeHelm);
		atracciones.add(bosqueNegro);
		atracciones.add(erebor);
		atracciones.add(esgaroth);
		atracciones.add(lothlorein);
		atracciones.add(laComarca);
		atracciones.add(minasTirith);
		atracciones.add(mordor);
		atracciones.add(moria);
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
	public void testDeBuscarAtraccionPorNombre() {
		assertEquals(moria,Atraccion.buscarAtraccionPorNombre(moria.getId(), atracciones));
		assertEquals(minasTirith,Atraccion.buscarAtraccionPorNombre(minasTirith.getId(), atracciones));
		assertEquals(laComarca,Atraccion.buscarAtraccionPorNombre(laComarca.getId(), atracciones));
		assertEquals(mordor,Atraccion.buscarAtraccionPorNombre(mordor.getId(), atracciones));
		assertEquals(abismoDeHelm,Atraccion.buscarAtraccionPorNombre(abismoDeHelm.getId(), atracciones));
		assertEquals(lothlorein,Atraccion.buscarAtraccionPorNombre(lothlorein.getId(), atracciones));
		assertEquals(erebor,Atraccion.buscarAtraccionPorNombre(erebor.getId(), atracciones));
		assertEquals(bosqueNegro,Atraccion.buscarAtraccionPorNombre(bosqueNegro.getId(), atracciones));
		assertEquals(esgaroth,Atraccion.buscarAtraccionPorNombre(esgaroth.getId(), atracciones));
	}
}
