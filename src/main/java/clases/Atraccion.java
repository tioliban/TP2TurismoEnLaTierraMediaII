package clases;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Atraccion extends Base {

	private int cupo;

	public Atraccion(int id, String nombre, double tiempo, double costo, int cupo, TipoAtraccion tipo) {
		super("2." + id, nombre, tiempo, costo, tipo);
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
			if (atraccion.getId().equals(nombre)) {
				return atraccion;
			}
		}
		return null;
	}

	@Override
	public boolean tieneCupo() {
		return this.getCupo() >= 1;
	}

	@Override
	public HashMap<String, Atraccion> getAtracciones() {
		HashMap<String, Atraccion> salida = new HashMap<String, Atraccion>();
		salida.put(this.getId(), this);
		return salida;
	}

	@Override
	public String toString() {
		StringBuilder salida = new StringBuilder("atraccion:\n ");
		salida.append(super.getNombre());
		salida.append(", que es de tipo ");
		salida.append(super.getTipoAtraccion());
		salida.append(", con un costo de ");
		salida.append(super.getCosto());
		salida.append(" monedas de oro y con una duracion de ");
		salida.append(super.getTiempo());
		salida.append(" horas.\n");
		return salida.toString();
	}

}
