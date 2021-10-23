package clases;

import java.util.Iterator;
import java.util.List;

public class Atraccion extends Base {

	private int cupo;

	public Atraccion(int id, String nombre, double tiempo, double costo, int cupo, TipoAtraccion tipo) {
		super(id, nombre, tiempo, costo, tipo);
		this.setCupo(cupo);
	}

	/**
	 * @pre No tiene.
	 * @post Retorno el cupo de la atraccion.
	 * @return Cupo disponible de la Atraccion.
	 */
	public int getCupo() {
		return cupo;
	}

	/**
	 * @pre No Tiene.
	 * @post Se actualizo el cupo de la Atraccion.
	 * @param cupo Cantidad de cupo de la atraccion a actualizar.
	 * @return No tiene.
	 */
	private void setCupo(int cupo) {
		this.cupo = cupo;
	}

	/**
	 * @pre No tiene.
	 * @post Se redujo en uno el cupo disponible de la atraccion,
	 */
	public void subirAtraccion() {
		this.cupo--;
	}

	/**
	 * @pre No tiene.
	 * @post Se busco una atraccion por su nombre, en la lista de atracciones y
	 *       recupero la instancia de atracción asociada al nombre ingresado.
	 * @param nombre      Nombre de la atraccion a buscar.
	 * @param atracciones Lista de atracciones en la que buscaremos las atracciones.
	 * @return Retorno una instancia de atracción asociado al nombre ingresado.
	 */
	public static Atraccion buscarAtraccionPorNombre(String nombre, List<Atraccion> atracciones) {
		Iterator<Atraccion> indice = atracciones.iterator();
		while (indice.hasNext()) {
			Atraccion atraccion = indice.next();
			if (atraccion.getNombre().equals(nombre)) {
				return atraccion;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getNombre() + ", con un costo de " + this.getCosto()
				+ " monedas de oro, un tiempo necesario para recorrerlo de " + super.getTiempo() + " horas, un cupo de "
				+ this.getCupo() + " usuarios y su tipo de atraccion es " + this.getTipoAtraccion().toString();
	}

}
