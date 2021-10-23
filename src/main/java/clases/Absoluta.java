package clases;

import java.util.ArrayList;

import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class Absoluta extends Promocion {

	public Absoluta(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> nombresDeAtracciones) throws ExcepcionDeBase, ExcepcionDePromocion, ExcepcionDeAtraccion {
		super(id, nombre, "Absoluta", tiempo, costo, tipoAtraccion, nombresDeAtracciones);
	}

	@Override
	public String imprimir() {
		String retorno = "";
		retorno += this.getNombresDeAtracciones().get(0) + " y ";
		retorno += this.getNombresDeAtracciones().get(1);
		return retorno;
	}

	@Override
	public String toString() {
		return this.getNombre() + ", que incluye a las atracciones de " + this.imprimir() + " que son de tipo "
				+ this.getTipoAtraccion().toString() + ", con un costo de " + this.getCosto()
				+ " monedas de oro, un tiempo necesario para recorrerlas de " + super.getTiempo() + " horas";
	}
}