package clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

	private int id;
	private int productos;
	private String nombre;
	private double tiempo;
	private double presupuesto;
	private TipoAtraccion preferencia;
	private List<Base> itinerario;

	public Usuario(int id, String nombre, double tiempo, double presupuesto, TipoAtraccion preferencia) {
		this.setId(id);
		this.setNombre(nombre);
		this.setTiempo(tiempo);
		this.setPresupuesto(presupuesto);
		this.setPreferencia(preferencia);
		this.itinerario = new ArrayList<Base>();
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el id del usuario.
	 * @return Id del Usuario.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el id del usuario.
	 * @param id Nuevo id a actualizar.
	 */
	private void setId(int id) {
		this.id = id;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno la cantidad de productos del Usuario.
	 * @return Productos del Usuario.
	 */
	public int getProductos() {
		return productos;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo la cantidad de productos del usuario.
	 * @param productos Nuevo id a actualizar.
	 */
	public void setProductos(int productos) {
		this.productos = productos;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el nombre del usuario.
	 * @return El nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @pre No tiene.
	 * @post Se asigno y valido el nombre del usuario.
	 * @param nombre Nuevo nombre que tendra el usuario.
	 */
	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el tiempo disponible del usuario.
	 * @return El tiempo disponible del usuario.
	 */
	public double getTiempo() {
		return tiempo;
	}

	/**
	 * @pre No tiene.
	 * @post Se asigno y valido el tiempo disponible del usuario.
	 * @param tiempo Nueva cantidad de tiempo que tendra el usuario.
	 */
	private void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el presupuesto del usuario.
	 * @return El presupuesto que tiene el usuario.
	 */
	public double getPresupuesto() {
		return presupuesto;
	}

	/**
	 * @pre No tiene.
	 * @post Se valido y asigno el presupuesto que dispone el usuario.
	 * @param presupuesto Nueva cantidad de dinero que tendra el usuario.
	 */
	private void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno la preferencia de atracciones que tiene el usuario.
	 * @return Tipo de preferencia del usuario.
	 */
	public TipoAtraccion getPreferencia() {
		return preferencia;
	}

	/**
	 * @pre No tiene.
	 * @post Se asigno el tipo de preferencia que tiene el usuario.
	 * @param preferencia Tipo de atraccion que prefiere el usuario.
	 */
	private void setPreferencia(TipoAtraccion preferencia) {
		this.preferencia = preferencia;
	}

	/**
	 * @pre No tiene.
	 * @post Se agrego una sugerencia al itinerario si fue aceptada.
	 * @param sugerencia Una promoción o atracción.
	 * @param decision   Un verdadero o falso, según haya sido la desicion ingresada
	 *                   por consola.
	 * @return Retorna un verdadero o falso, para informarle a la atraccion o
	 *         promocion que la sugerencia fue aceptada.
	 */
	public boolean aceptarSugerencia(Base sugerencia, boolean decision) {
		if (decision) {
			this.tiempo -= sugerencia.getTiempo();
			this.presupuesto -= sugerencia.getCosto();
			this.itinerario.add(sugerencia);
		}
		return decision;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno una lista con los productos que acepto el usuario.
	 * @return Itinerario del usuario.
	 */
	public List<Base> getItinerario() {
		return itinerario;
	}

	/**
	 * @pre No tiene.
	 * @post Se agrego un producto al itinerario del usuario.
	 * @param agregar Producto a agregar.
	 */
	public void addItinerario(Base agregar) {
		itinerario.add(agregar);
	}

	@Override
	public String toString() {
		return this.getNombre() + ", con un tiempo disponible de " + this.getTiempo() + ", un presupuesto de "
				+ this.getPresupuesto() + " monedas de oro y una preferencia para las atracciones de tipo "
				+ this.getPreferencia().toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, preferencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
		return id == other.id && Objects.equals(nombre, other.nombre) && preferencia == other.preferencia;
	}

}
