package clases;

import java.util.HashMap;

public abstract class Base implements Comparable<Base> {

	private String id;
	private String nombre;
	private double tiempo;
	private double costo;
	private TipoAtraccion tipo;

	public Base(String id, String nombre, double tiempo, double costo, TipoAtraccion tipo) {
		this.setId(id);
		this.setNombre(nombre);
		this.setTiempo(tiempo);
		this.setCosto(costo);
		this.setTipoAtraccion(tipo);

	}

	/**
	 * @pre No tiene.
	 * @post Retorno el id de la atraccion o promocion.
	 * @return Id de la Atraccion o Promocion.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el id de la Atraccion o Promocion.
	 * @param id Nuevo id a actualizar.
	 */
	private void setId(String id) {
		this.id = id;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el nombre de la atraccion o promocion.
	 * @return Nombre de la Atraccion o Promocion.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el nombre de la Atraccion o Promocion.
	 * @param nombre Nuevo nombre a actualizar.
	 */
	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno la duracion de la atraccion o promocion.
	 * @return Duración de la Atraccion o Promocion.
	 */
	public double getTiempo() {
		return tiempo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo la duracion de la Atraccion o Promocion.
	 * @param tiempo Nueva duración a actualizar.
	 */
	private void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el costo de la atraccion o promocion.
	 * @return Costo de la Atraccion o Promocion.
	 */
	public double getCosto() {
		return costo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el costo de la Atraccion o Promocion.
	 * @param costo Cantidad de monedas que requiere la atraccion o promocion a
	 *              actualizar.
	 * @return No tiene.
	 */
	private void setCosto(double costo) {
		this.costo = costo;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el tipo de atraccion o tipo de atracciones contenidas en la
	 *       promocion.
	 * @return Tipo de Atraccion o Promocion.
	 */
	public TipoAtraccion getTipoAtraccion() {
		return tipo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el tipo de la Atraccion o Promocion.
	 * @param tipo Tipo que requiere la atraccion o promocion a actualizar.
	 */
	private void setTipoAtraccion(TipoAtraccion tipo) {
		this.tipo = tipo;
	}

	/**
	 * @pre No tiene.
	 * @post Se redujo en uno el cupo de la atraccion
	 */
	public abstract void subirAtraccion();

	/**
	 * @pre No tiene.
	 * @post Retorno verdad si tiene cupo para que se pueda ofrecer a un usuario:
	 * @return Valor logico indicando la posibilidad de ofrecer el produto
	 */
	public abstract boolean tieneCupo();

	/**
	 * @pre No tiene.
	 * @post Retorno una coleccion con todas las atracciones que contiene el
	 *       producto.
	 * @return Coleccion de atracciones.
	 */
	public abstract HashMap<String, Atraccion> getAtracciones();

	@Override
	public int compareTo(Base otro) {
		if (this.equals(otro))
			return 0;
		else if (this.id.startsWith("1.") && otro.getId().startsWith("1."))
			return 0;
		else if (this.id.startsWith("1.") && otro.getId().startsWith("2."))
			return -1;
		else if (this.id.startsWith("2.") && otro.getId().startsWith("1."))
			return 1;
		else {
			if (this.costo == otro.getCosto())
				if (this.tiempo == otro.getTiempo())
					return 0;
				else if (this.tiempo > otro.getTiempo())
					return -1;
				else
					return 1;
			else if (this.costo > otro.getCosto())
				return -1;
			else
				return 1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Base)) {
			if (obj instanceof String)
				return this.id.equals(obj);
			else
				return false;
		}
		Base other = (Base) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
