package clases;

import java.util.HashMap;

public class AxB extends Promocion {
	private String atraccionGratis;

	public AxB(int id, String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			HashMap<String, Atraccion> atracciones, String atraccionGratis) {
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
}
