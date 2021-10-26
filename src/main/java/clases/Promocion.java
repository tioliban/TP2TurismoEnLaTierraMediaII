package clases;

import java.util.ArrayList;

public abstract class Promocion extends Base {
	private ArrayList<String> atracciones;
	private String promo;
	private double descuento;

	public Promocion(int id, String nombre, String promo, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> atracciones) {
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

	/**
	 * @pre No tiene
	 * @post Retorno la lista con los nombres de atracciones que incluye la
	 *       promocion.
	 * @return the nombresDeAtracciones
	 */
	public ArrayList<String> getAtracciones() {
		return atracciones;
	}

}
