package clases;

import java.util.ArrayList;

public class Absoluta extends Promocion {

	public Absoluta(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones) {
		super(id, nombre, "Absoluta", tiempo, costo, tipoAtraccion, atracciones);
	}

	@Override
	public String toString() {
		StringBuilder salida = new StringBuilder(super.getNombre());
		salida.append(", que incluye a las atracciones de tipo ");
		salida.append(super.getTipoAtraccion());
		salida.append(", con un costo de ");
		salida.append(super.getCosto());
		salida.append(" monedas de oro, un tiempo necesario para recorrerlas de ");
		salida.append(super.getTiempo());
		salida.append(" horas");
		return salida.toString();
	}
}