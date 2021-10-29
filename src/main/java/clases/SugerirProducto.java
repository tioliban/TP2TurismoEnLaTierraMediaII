package clases;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import comparadores.NombreDeAtraccionComparador;
import comparadores.PrecioYTiempoDeAtraccionComparador;
import comparadores.TiempoDeAtraccionComparador;
import comparadores.TipoDeAtraccionComparador;
import comparadores.TipoDeAtraccionDeLaPromocionComparador;

public class SugerirProducto {
	private List<Usuario> usuarios = null;
	private List<Promocion> promociones = null;
	private List<Atraccion> atracciones = null;
	private Scanner teclado;

	public SugerirProducto(List<Usuario> losUsuarios, List<Promocion> lasPromociones, List<Atraccion> lasAtracciones) {
		this.setUsuarios(losUsuarios);
		this.setPromociones(lasPromociones);
		this.setAtracciones(lasAtracciones);
		teclado = new Scanner(System.in);
	}

	/**
	 * @pre No tiene.
	 * @post Retorno una lista con los usuarios registrados en el sistema.
	 * @return Lista con los usuarios.
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @pre No tiene.
	 * @post Se actualizó la lista de usuarios que están registrados en el
	 *       sistema.
	 * @param usuarios Los usuarios a ser actualizados.
	 */
	private void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno una lista con las promociones registradas en el sistema.
	 * @return Lista con las promociones.
	 */
	public List<Promocion> getPromociones() {
		return promociones;
	}

	/**
	 * @pre No tiene.
	 * @post Se actualizó la lista de los usuarios que están registrados en el
	 *       sistema.
	 * @param promociones Las promociones a ser actualizadas.
	 */
	private void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno una lista con las atracciones registradas en el sistema.
	 * @return Lista con las atracciones.
	 */
	public List<Atraccion> getAtracciones() {
		return atracciones;
	}

	/**
	 * @pre No tiene.
	 * @post Se actualizó la lista de las atracciones que estan registradas en el
	 *       sistema.
	 * @param atracciones Las atracciones a ser actualizadas.
	 */
	private void setAtracciones(List<Atraccion> atracciones) {
		this.atracciones = atracciones;
	}

	/**
	 * @pre No tiene.
	 * @post Retorno un valor de verdad informando si un usuario determinado ya
	 *       visito una atraccion determinada.
	 * @param usuario  Usuario a consultar si ya visitó un producto determinada.
	 * @param producto Producto a buscar determinada a verificar.
	 * @return Retorna un valor logico si el usuario visito o no la atraccion.
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean laVisito(Usuario usuario, String producto) {
		boolean retorno = true;
		if (usuario.getItinerario().contains(producto)) {
			return false;
		} else if (producto.startsWith("2.")) {
			for (Base itinerario : usuario.getItinerario()) {
				if (itinerario.getId().startsWith("1.")) {
					Promocion promo = (Promocion) itinerario;
					return retorno &= !promo.getAtracciones().contains(producto);
				}

			}
		}
		return retorno;
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirió todas las promociones y atracciones posibles para un
	 *       usuario determinado.
	 * @param usuario Usuario a ser sugerido los productos.
	 */
	public void sugerirPromocionConPreferencia(Usuario usuario) {
		for (Promocion laPromocion : this.getPromociones()) {
			boolean tieneCupo = true, noLaVisito = true;
			for (String atraccionDeLaPromocion : laPromocion.getAtracciones()) {
				tieneCupo = tieneCupo
						&& (Atraccion.buscarAtraccionPorNombre(atraccionDeLaPromocion, atracciones).getCupo() >= 1);
				noLaVisito &= this.laVisito(usuario, atraccionDeLaPromocion);
			}
			if ((usuario.getPreferencia() == laPromocion.getTipoAtraccion()) && tieneCupo
					&& (laPromocion.getTiempo() <= usuario.getTiempo())
					&& (laPromocion.getCosto() <= usuario.getPresupuesto()) && noLaVisito)
				if (usuario.aceptarSugerencia(laPromocion, this.respuesta(laPromocion)))
					this.subirPromocion(laPromocion);
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
	private boolean respuesta(Promocion laPromo) {
		System.out.println("Promo " + laPromo.toString());
		System.out.println("Presione \"1\", de lo contario presione cualquier otro numero...");
		teclado.nextInt();
		int respuesta = 0;
		respuesta = teclado.nextInt();
		return respuesta == 1;
	}	
		

	/**
	 * @pre No tiene.
	 * @post Se restó en uno el cupo de todas las atracciones que incluye la
	 *       promocion.
	 * @param laPromocion Promocion que contiene las atracciones a disminuir su
	 *                    cupo.
	 */
	private void subirPromocion(Promocion laPromocion) {
		for (String atraccion : laPromocion.getAtracciones()) {
			Atraccion.buscarAtraccionPorNombre(atraccion, this.getAtracciones()).subirAtraccion();
		}
	}

	/**
	 * @pre No Tiene.
	 * @post Se sugirio todas las promociones restantes a un usuario determinado.
	 * @param usuario Usuario a sugerir las promociones restantes.
	 */
	public void sugerirPromocionSinPreferencia(Usuario usuario) {
		for (Promocion laPromocion : this.getPromociones()) {
			boolean tieneCupo = true, noLaVisito = true;
			for (String atraccionDeLaPromocion : laPromocion.getAtracciones()) {
				tieneCupo = tieneCupo
						&& (Atraccion.buscarAtraccionPorNombre(atraccionDeLaPromocion, atracciones).getCupo() >= 1);
				noLaVisito = noLaVisito && this.laVisito(usuario, atraccionDeLaPromocion);
			}
			if (tieneCupo && (laPromocion.getTiempo() <= usuario.getTiempo())
					&& (laPromocion.getCosto() <= usuario.getPresupuesto()) && noLaVisito)
				if (usuario.aceptarSugerencia(laPromocion, this.respuesta(laPromocion)))
					this.subirPromocion(laPromocion);
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
				if (usuario.aceptarSugerencia(laAtraccion, this.respuesta(laAtraccion)))
					laAtraccion.subirAtraccion();
		}
	}

	/**
	 * @pre No tiene.
	 * @post Se evaluo la respuesta ingresada por el usuario por la consola y
	 *       retorno un valor lógico representando la misma.
	 * @param laAtraccion Atraccion que se muestra para tomar una decision.
	 * @return La desicion traducida a un valor lógico.
	 */
	private boolean respuesta(Atraccion laAtraccion) {
		System.out.println("Atracci�n: " + laAtraccion.toString());
		System.out.println("Presione \"1\" para aceptar, de lo contrario presione cualquier otro numero.");
		int respuesta = 0;
		respuesta = teclado.nextInt();
		return respuesta == 1;
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de promociones según su tipo de atraccion.
	 * @param promociones Lista de promociones a ordenar.
	 */
	public void ordenarPromocionesPorTipo(List<Promocion> promociones) {
		Collections.sort(promociones, new TipoDeAtraccionDeLaPromocionComparador());
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de atracciones según su tipo de atraccion.
	 * @param atracciones Lista de atracciones a ordenar.
	 */
	public void ordenarAtraccionesPorTipo(List<Atraccion> atracciones) {
		Collections.sort(atracciones, new TipoDeAtraccionComparador());
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de atracciones según su precio y duracion.
	 * @param promociones Lista de atracciones a ordenar.
	 */
	public void ordenarAtraccionesPorPrecioYTiempo(List<Atraccion> atracciones) {
		Collections.sort(atracciones, new PrecioYTiempoDeAtraccionComparador());
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de atracciones según su duracion.
	 * @param promociones Lista de atracciones a ordenar.
	 */
	public void ordenarAtraccionesPorTiempo(List<Atraccion> atracciones) {
		Collections.sort(atracciones, new TiempoDeAtraccionComparador());
	}

	/**
	 * @pre No tiene.
	 * @post Se ordeno la lista de atracciones según nombre.
	 * @param promociones Lista de atracciones a ordenar.
	 */
	public void ordenarAtraccionesPorNombre(List<Atraccion> atracciones) {
		Collections.sort(atracciones, new NombreDeAtraccionComparador());
	}

}
