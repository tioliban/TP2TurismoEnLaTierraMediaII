package clases;

import java.util.ArrayList;

public class Absoluta extends Promocion {

	public Absoluta(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones) {
		super(id, nombre, "Absoluta", tiempo, costo, tipoAtraccion, atracciones);
	}
}