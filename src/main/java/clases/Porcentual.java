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
		return this.getId() + ", que incluye a las atracciones de " + " que son de tipo "
				+ this.getTipoAtraccion().toString() + ", con un costo de " + this.getCosto()
				+ " monedas de oro, un tiempo necesario para recorrerlas de " + super.getTiempo() + " horas";
	}
}
