package clases;

import java.util.ArrayList;


import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public class AxB extends Promocion {
	private String atraccionGratis;

	public AxB(String nombre, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> nombresDeAtracciones, String atraccionGratis)
			throws ExcepcionDeBase, ExcepcionDePromocion, ExcepcionDeAtraccion {
		super(nombre, "AxB", tiempo, costo, tipoAtraccion, nombresDeAtracciones);
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
	public String imprimir() {
		String retorno = "";
		retorno += this.getNombresDeAtracciones().get(0) + ", ";
		retorno += this.getNombresDeAtracciones().get(1) + " y ";
		retorno += this.getNombresDeAtracciones().get(2);
		return retorno;
	}

	@Override
	public String toString() {
		return this.getNombre() + ", que incluye a las atracciones de " + this.imprimir() + " que son de tipo "
				+ this.getTipoAtraccion().toString() + ", con un costo de " + this.getCosto()
				+ " monedas de oro, un tiempo necesario para recorrerlas de " + super.getTiempo() + " horas";
	}
}
