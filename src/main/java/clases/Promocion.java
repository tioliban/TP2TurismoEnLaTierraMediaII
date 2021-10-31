package clases;

import java.util.HashMap;

public abstract class Promocion extends Base {
	private HashMap<String, Atraccion> atracciones = new HashMap<String, Atraccion>();
	private String promo;
	private double descuento;

	public Promocion(int id, String nombre, String promo, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			HashMap<String, Atraccion> atracciones) {
		super("1." + id, nombre, tiempo, costo, tipoAtraccion);
		this.promo = promo;
		this.atracciones = atracciones;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el porcentaje de descuento de la promocion.
	 * @return Porcentaje de descuento.
	 */
	public double getDescuento() {
		return descuento;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el porcentaje de descuento de la Promocion.
	 * @param descuento Nuevo porcentaje de descuento.
	 */
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el tipo de promocion.
	 * @return Tipo de promocion.
	 */
	public String getPromo() {
		return promo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el tipo de Promocion.
	 * @param tipo Nuevo nombre a actualizar.
	 */
	public void setPromo(String promo) {
		this.promo = promo;
	}

	@Override
	public boolean tieneCupo() {
		boolean salida = true;
		for (Atraccion id : this.getAtracciones().values()) {
			salida &= id.tieneCupo();
		}
		return salida;
	}

	@Override
	public HashMap<String, Atraccion> getAtracciones() {
		return atracciones;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo las atracciones de Promocion.
	 * @param atracciones the atracciones to set
	 */
	public void setAtracciones(HashMap<String, Atraccion> atracciones) {
		for (String id : this.atracciones.keySet()) {
			this.atracciones.put(id, atracciones.get(id));
		}
	}

	@Override
	public void subirAtraccion() {
		for (String ids : this.atracciones.keySet())
			this.atracciones.get(ids).subirAtraccion();
	}

	@Override
	public String toString() {
		StringBuilder salida = new StringBuilder("promocion:\n ");
		salida.append(super.getNombre());
		salida.append(", que contiene atracciones de tipo ");
		salida.append(super.getTipoAtraccion());
		salida.append(", con un costo total de ");
		salida.append(super.getCosto());
		salida.append(" monedas de oro y con una duracion total de ");
		salida.append(super.getTiempo());
		salida.append(" horas.\n");
		return salida.toString();
	}

}
