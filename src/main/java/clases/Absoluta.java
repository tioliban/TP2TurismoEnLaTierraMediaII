package clases;

import java.util.ArrayList;

public class Absoluta extends Promocion {

	public Absoluta(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones) {
		super(id, nombre, "Absoluta", tiempo, costo, tipoAtraccion, atracciones);
	}
 
	@Override
	public String toString() {
		return this.getNombre() + "\nque incluye a las atracciones de " + " que son de tipo "
				+ this.getTipoAtraccion().toString() + ", con un costo de " + this.getCosto()
				+ " monedas de oro, un tiempo necesario para recorrerlas de " + super.getTiempo() + " horas";
	}
}