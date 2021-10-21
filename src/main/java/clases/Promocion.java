package clases;

import java.util.ArrayList;

import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;

public abstract class Promocion extends Base {
	private ArrayList<String> nombresDeAtracciones;
	private String tipo;
	private double descuento;

	public Promocion(String nombre, String tipo, double tiempo, double costo, TipoAtraccion tipoAtraccion,
			ArrayList<String> nombresDeAtracciones/**, List<Atraccion> atracciones*/) throws ExcepcionDeBase, ExcepcionDePromocion {
		super(nombre, tiempo, costo, tipoAtraccion);
		this.tipo = tipo;
		this.nombresDeAtracciones = nombresDeAtracciones;
		//this.setNombresDeAtracciones(nombresDeAtracciones, atracciones);
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
	public String getTipo() {
		return tipo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el tipo de Promocion.
	 * @param tipo Nuevo nombre a actualizar.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * @post Se actualizó los nombres de las atracciones que incluye la promocion.
	 * @param nombresDeAtracciones Nombres de atracciones a actualizar.
	 * @throws ExcepcionDePromocion Informo la existencia de un error al momento de
	 *                              asignar los nombres de las atracciones que
	 *                              incluye la promocion, ya que alguna de ellas
	 *                              tiene un tipo de atraccion que no coincide con
	 *                              el tipo de atracciones contenida en la
	 *                              promocion.
	 
	private void setNombresDeAtracciones(String[] nombresDeAtracciones, List<Atraccion> atracciones)
			throws ExcepcionDePromocion {
		boolean validar = true;
		ArrayList<String> retorno = new ArrayList<String>();
		if (nombresDeAtracciones.length > 1) {
			for (String nombre : nombresDeAtracciones) {
				validar = validar && this.getTipoAtraccion() == Atraccion.buscarAtraccionPorNombre(nombre, atracciones)
						.getTipoAtraccion();
			}
			if (validar)
				for (String nombre : nombresDeAtracciones) {
					retorno.add(nombre);
				}
			if (nombresDeAtracciones.length == retorno.size())
				this.nombresDeAtracciones = retorno;
			else
				throw new ExcepcionDePromocion(
						"asignar la lista de atracciones, ya que el tipo de atraccion de una o más atracciones"
								+ " no coincide con el tipo de atracciones de la promocion");
		} else
			throw new ExcepcionDePromocion("asignar la lista de atracciones ya que la lista de atracciones esta vacía");
	}
	*/
	
	/**
	 * @pre No tiene.
	 * @post Se recuperó el nombre de todas las atracciones contenidas en la
	 *       promocion.
	 * @return String con los nombres de las atracciones que la componen.
	 */
	public abstract String imprimir();

}
