package clases;

import java.util.ArrayList;

import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public abstract class Promocion extends Base {
	private ArrayList<String> nombresDeAtracciones;
	private String promo;
	private double descuento;

	public Promocion(int id, String nombre, String promo, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> nombresDeAtracciones) throws ExcepcionDeBase, ExcepcionDePromocion {
		super(id, nombre, tiempo, costo, tipoAtraccion);
		this.promo = promo;
		this.nombresDeAtracciones = nombresDeAtracciones;
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
	public ArrayList<String> getNombresDeAtracciones() {
		return nombresDeAtracciones;
	}

	/**
	 * @pre No tiene.
	 * @post Se recuperó el nombre de todas las atracciones contenidas en la
	 *       promocion.
	 * @return String con los nombres de las atracciones que la componen.
	 */
	public abstract String imprimir();

}
