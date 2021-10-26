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
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class TestPromocionAxB {

	AxB promo;
	ArrayList<String> nombres = new ArrayList<>();
	List<Atraccion> atracciones;
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
		nombres.add(moria.getId());
		nombres.add(mordor.getId());
		nombres.add(bosqueNegro.getId());
		promo = new AxB(1, "Segunda", 9, 28, TipoAtraccion.AVENTURA, nombres, bosqueNegro.getId());
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
		atracciones = null;
		promo = null;
	}

	@Test
	public void testDeGetAtraccionGratis() {
		assertEquals(bosqueNegro.getId(), promo.getAtraccionGratis());
	}

	@Test
	public void testDeGetAtracciones() {
		assertEquals(nombres, promo.getAtracciones());
	}

	@Test
	public void testDeToString() {
		String test = "Segunda, que incluye a las atracciones de Moria, Mordor y Bosque Negro que son de tipo AVENTURA, "
				+ "con un costo de 35.0 monedas de oro, un tiempo necesario para recorrerlas de 9.0 horas";
		assertEquals(test, promo.toString());
	}
}
