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
		return this.getNombre() + "\nIncluye atracciones de " + this.getTipoAtraccion().toString()
				+ "\nCosto: " + this.getCosto() + " monedas de oro"
				+ "\nTiempo necesario para recorrerlas: " + super.getTiempo() + " horas";
	}
}
