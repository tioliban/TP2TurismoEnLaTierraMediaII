package clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import comparadores.PrimeroAventura;
import comparadores.PrimeroDegustacion;
import comparadores.PrimeroPaisaje;

public class SugerirProducto {
	private HashMap<String, Usuario> usuarios = null;
	private HashMap<String, Base> productos = null;
	private LinkedList<Base> procesamiento;
	private ArrayList<String> aventura, degustacion, paisaje;
	private Scanner teclado;

	public SugerirProducto(HashMap<String, Usuario> losUsuarios, HashMap<String, Base> losProductos,
			HashMap<String, Atraccion> lasAtracciones) {
		this.usuarios = losUsuarios;
		this.productos = losProductos;
		this.ordenarListas();
		teclado = new Scanner(System.in);
	}

	/**
	 * 
	 */
	public void sugerirTodosLosProductos() {
		for (String id : this.usuarios.keySet()) {
			this.filtrarPreferencia(this.usuarios.get(id));
		}
	}

	/**
	 * @pre No tiene.
	 * @post Retorno verdadero si el usuario aún no visito un producto determinado.
	 * @param usuario  Usuario a consultar si ya visitó un producto determinada.
	 * @param producto Producto a buscar determinada a verificar.
	 * @return Retorna un valor logico si el usuario visito o el producto.
	 */
	public boolean puedoVisitarla(Usuario usuario, Object producto) {
		boolean retorno = true;
		if (usuario.getItinerario().isEmpty())
			return retorno;
		else if (usuario.getItinerario().contains(producto))
			return !retorno;
		else {
			for (Base itinerario : usuario.getItinerario()) {
				return retorno &= !itinerario.getAtracciones().containsKey(producto);
			}
		}
		return retorno;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno verdadero si el usuario puede permitirse adquirir un producto
	 *       determinado.
	 * @param usuario  Usuario a evaluar si puede permitirse el producto..
	 * @param producto Producto a utilizar como referencia
	 * @return Retorna un valor logico si el usuario puede permiterse o no el
	 *         producto.
	 */
	public boolean puedoSubirme(Usuario usuario, Base producto) {
		if (usuario.getTiempo() >= producto.getTiempo() && usuario.getPresupuesto() >= producto.getCosto())
			return true;
		else
			return false;
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirió todos los promociones para un usuario determinado, tomando
	 *       en cuenta las restricciones de la consigna.
	 * @param usuario Usuario a ser sugerido los productos.
	 */
	public void filtrarPreferencia(Usuario usuario) {
		if (usuario.getPreferencia().equals(TipoAtraccion.AVENTURA))
			this.sugerirProducto(usuario, this.aventura);
		else if (usuario.getPreferencia().equals(TipoAtraccion.DEGUSTACION))
			this.sugerirProducto(usuario, this.degustacion);
		else
			this.sugerirProducto(usuario, this.degustacion);
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirió todos los promociones para un usuario determinado, tomando
	 *       en cuenta las restricciones de la consigna.
	 * @param usuario Usuario a ser sugerido los productos.
	 */
	public void sugerirProducto(Usuario usuario, ArrayList<String> lista) {
		Base producto;
		for (String idProducto : lista) {
			producto = this.productos.get(idProducto);
			if (this.puedoVisitarla(usuario, producto))
				if (producto.tieneCupo() && this.puedoSubirme(usuario, producto))
					if (this.respuesta(producto))
						usuario.aceptarSugerencia(producto);
		}

	}

	/**
	 * @pre No tiene.
	 * @post Se evaluo la respuesta ingresada por el usuario por la consola y
	 *       retorno un valor lógico representando la misma.
	 * @param laPromo Promocion que se muestra para tomar una decision.
	 * @return La desicion traducida a un valor lógico.
	 */
	public boolean respuesta(Base producto) {
		StringBuilder salida = new StringBuilder("Si desea aceptar la ");
		salida.append(producto.toString());
		salida.append("Presione \"1\", de lo contario presione cualquier tecla");
		System.out.println(salida);
		return 1 == teclado.nextInt();
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno todas las listas que contienen los ids de los productos a
	 *       ofrecer, a modo de indice para consultar en el hashmap que contiene
	 *       todos los productos y evitar el reordenamiento constante de los
	 *       productos.
	 */
	public void ordenarListas() {
		aventura = new ArrayList<String>();
		degustacion = new ArrayList<String>();
		paisaje = new ArrayList<String>();
		procesamiento = new LinkedList<Base>(this.productos.values());
		this.ordenamiento(procesamiento, TipoAtraccion.AVENTURA);
		for (Base id : procesamiento) {
			aventura.add(id.getId());
		}
		this.ordenamiento(procesamiento, TipoAtraccion.DEGUSTACION);
		for (Base id : procesamiento) {
			degustacion.add(id.getId());
		}
		this.ordenamiento(procesamiento, TipoAtraccion.PAISAJE);
		for (Base id : procesamiento) {
			paisaje.add(id.getId());
		}
		procesamiento = null;
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de promociones según el tipo de preferencia de
	 *       atraccion determinado.
	 * @param ordenar   Lista de productos a ordenar.
	 * @param prioridad Criterio de preferencia a utilizar en el ordenamiento.
	 */
	public void ordenamiento(LinkedList<Base> ordenar, TipoAtraccion prioridad) {
		if (prioridad.equals(TipoAtraccion.AVENTURA))
			Collections.sort(ordenar, new PrimeroAventura());
		else if (prioridad.equals(TipoAtraccion.DEGUSTACION))
			Collections.sort(ordenar, new PrimeroDegustacion());
		else
			Collections.sort(ordenar, new PrimeroPaisaje());
	}

}
