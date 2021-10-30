package clases;

import java.util.HashMap;

public class Porcentual extends Promocion {

	double porcentajeDescuento;

	public Porcentual(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			HashMap<String,Atraccion> atracciones, double porcentaje) {
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
}
