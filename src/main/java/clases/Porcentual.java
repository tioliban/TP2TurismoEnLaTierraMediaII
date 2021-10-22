package clases;

import java.util.ArrayList;

import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class Porcentual extends Promocion {

	double porcentajeDescuento;

	public Porcentual(String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> nombresDeAtracciones, double porcentaje)
			throws ExcepcionDeBase, ExcepcionDePromocion, ExcepcionDeAtraccion {
		super(nombre, "Porcentual", tiempo, costo, tipoAtraccion, nombresDeAtracciones);
		this.porcentajeDescuento = porcentaje;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el procentaje de descuento que posee la promocion.
	 * @return Cantidad de descuento en porcentaje que tiene la promocion.
	 */
	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
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
