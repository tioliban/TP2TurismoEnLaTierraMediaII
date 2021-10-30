package clases;

import java.util.HashMap;

public class Absoluta extends Promocion {

	public Absoluta(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			HashMap<String, Atraccion> atracciones) {
		super(id, nombre, "Absoluta", tiempo, costo, tipoAtraccion, atracciones);
	}
}