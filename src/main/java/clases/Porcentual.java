package clases;

import java.util.ArrayList;

public class Porcentual extends Promocion {

	double porcentajeDescuento;

	public Porcentual(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones, double porcentaje) {
		super(id, nombre, "Porcentual", tiempo, costo, tipoAtraccion, atracciones);
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
	public String toString() {
		StringBuilder salida = new StringBuilder(super.getNombre());
		salida.append(", que incluye a las atracciones de tipo ");
		salida.append(super.getTipoAtraccion());
		salida.append(", con un costo de ");
		salida.append(super.getCosto());
		salida.append(" monedas de oro, un tiempo necesario para recorrerlas de ");
		salida.append(super.getTiempo());
		salida.append(" horas");
		return salida.toString();	}
}
