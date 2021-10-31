package clases;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import comparadores.PrimeroAventura;
import comparadores.PrimeroDegustacion;
import comparadores.PrimeroPaisaje;

public class SugerirProducto {
	private HashMap<String, Usuario> usuarios = null;
	private HashMap<String, Base> productos = null;
	private LinkedList<Base> aventura, degustacion, paisaje;
	private Scanner teclado;

	public SugerirProducto(HashMap<String, Usuario> losUsuarios, HashMap<String, Base> losProductos,
			HashMap<String, Atraccion> lasAtracciones) {
		this.usuarios = losUsuarios;
		this.productos = losProductos;
		teclado = new Scanner(System.in);
	}

	/**
	 * @pre No tiene.
	 * @post Retorno un valor de verdad informando si un usuario determinado ya
	 *       visito una atraccion determinada.
	 * @param usuario  Usuario a consultar si ya visitó un producto determinada.
	 * @param producto Producto a buscar determinada a verificar.
	 * @return Retorna un valor logico si el usuario visito o no la atraccion.
	 */
	public boolean laVisito(Usuario usuario, Object producto) {
		boolean retorno = true;
		if (usuario.getItinerario().contains(producto))
			return false;
		else {
			for (Base itinerario : usuario.getItinerario()) {
				return retorno &= !itinerario.getAtracciones().containsKey(producto);
			}
		}
		return retorno;
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirió todas las promociones y atracciones posibles para un usuario
	 *       determinado.
	 * @param usuario Usuario a ser sugerido los productos.
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void sugerirPromocionConPreferencia(Usuario usuario) {
		for (Promocion laPromocion : this.getPromociones()) {
			boolean tieneCupo = true, noLaVisito = true;
			for (String atraccionDeLaPromocion : laPromocion.getAtracciones()) {
				if (atracciones.contains(atraccionDeLaPromocion))
					tieneCupo &= atracciones.get(atracciones.indexOf(atraccionDeLaPromocion)).getCupo() >= 1;
				noLaVisito &= this.laVisito(usuario, atraccionDeLaPromocion);
			}
			if ((usuario.getPreferencia() == laPromocion.getTipoAtraccion()) && tieneCupo
					&& (laPromocion.getTiempo() <= usuario.getTiempo())
					&& (laPromocion.getCosto() <= usuario.getPresupuesto()) && noLaVisito)
				if (this.respuesta(laPromocion)) {
					usuario.aceptarSugerencia(laPromocion);
					this.subirPromocion(laPromocion);
				}
		}
		this.sugerirPromocionSinPreferencia(usuario);
	}

	/**
	 * @pre No tiene.
	 * @post Se evaluo la respuesta ingresada por el usuario por la consola y
	 *       retorno un valor lógico representando la misma.
	 * @param laPromo Promocion que se muestra para tomar una decision.
	 * @return La desicion traducida a un valor lógico.
	 */
	private boolean respuesta(Base producto) {
		StringBuilder salida = new StringBuilder("Si desea aceptar la ");
		salida.append(producto);
		salida.append("Presione \"1\", de lo contario presione cualquier tecla");
		System.out.println(salida);
		return 1 == teclado.nextInt();
	}

	/**
	 * @pre No tiene.
	 * @post Se restó en uno el cupo de todas las atracciones que incluye la
	 *       promocion.
	 * @param laPromocion Promocion que contiene las atracciones a disminuir su
	 *                    cupo.
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void subirPromocion(Promocion laPromocion) {
		for (String atraccion : laPromocion.getAtracciones()) {
			this.getAtracciones().get(this.getAtracciones().indexOf(atraccion)).subirAtraccion();
		}
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirio todas las promociones restantes a un usuario determinado.
	 * @param usuario Usuario a sugerir las promociones restantes.
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void sugerirPromocionSinPreferencia(Usuario usuario) {
		for (Promocion laPromocion : this.getPromociones()) {
			boolean tieneCupo = true, noLaVisito = true;
			for (String atraccionDeLaPromocion : laPromocion.getAtracciones()) {
				tieneCupo = tieneCupo && (atracciones.get(atracciones.indexOf(atraccionDeLaPromocion)).getCupo() >= 1);
				noLaVisito = noLaVisito && this.laVisito(usuario, atraccionDeLaPromocion);
			}
			if (tieneCupo && (laPromocion.getTiempo() <= usuario.getTiempo())
					&& (laPromocion.getCosto() <= usuario.getPresupuesto()) && noLaVisito)
				if (this.respuesta(laPromocion)) {
					usuario.aceptarSugerencia(laPromocion);
					this.subirPromocion(laPromocion);
				}
		}
		this.sugerirAtraccion(usuario);
	}

	/**
	 * @pre No tiene.
	 * @post Se sugirio todas las atracciones posibles para un usuario determinado.
	 * @param usuario Usuario a sugerir las atracciones.
	 */
	public void sugerirAtraccion(Usuario usuario) {
		this.ordenarAtraccionesPorPrecioYTiempo(this.getAtracciones());
		for (Atraccion laAtraccion : this.getAtracciones()) {
			if (laAtraccion.getCupo() >= 1 && laAtraccion.getTiempo() <= usuario.getTiempo()
					&& laAtraccion.getCosto() <= usuario.getPresupuesto()
					&& this.laVisito(usuario, laAtraccion.getId()))
				if (this.respuesta(laAtraccion)) {
					usuario.aceptarSugerencia(laAtraccion);
					laAtraccion.subirAtraccion();
				}
		}
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de promociones según el tipo de preferencia de
	 *       atraccion determinado.
	 * @param ordenar   Lista de productos a ordenar.
	 * @param prioridad Criterio de preferencia a utilizar en el ordenamiento.
	 */
	public void ordenamiento(List<Base> ordenar, TipoAtraccion prioridad) {
		if (prioridad.equals(TipoAtraccion.AVENTURA))
			Collections.sort(ordenar, new PrimeroAventura());
		else if (prioridad.equals(TipoAtraccion.DEGUSTACION))
			Collections.sort(ordenar, new PrimeroDegustacion());
		else
			Collections.sort(ordenar, new PrimeroPaisaje());
	}

}
