package clases;

import java.util.ArrayList;

public class AxB extends Promocion {
	private String atraccionGratis;

	public AxB(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones, String atraccionGratis) {
		super(id, nombre, "AxB", tiempo, costo, tipoAtraccion, atracciones);
		this.atraccionGratis = atraccionGratis;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el nombrede la traccion gratis.
	 * @return La atraccion que la promocion ofrece gratis.
	 */
	public String getAtraccionGratis() {
		return atraccionGratis;
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
