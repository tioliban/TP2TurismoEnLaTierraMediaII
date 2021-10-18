package persistencia;

public class DAOFactory {

	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImplementado();
	}

	public static AtraccionDAO getAtraccionDAO() {
		return new AtraccionDAOImplementado();
	}

	public static PromocionDAO getPromocionDAO() {
		return new PromocionDAOImplementado();
	}

	/**
	 * Dejamos preparado esto para la siguiente mejora del trabajo si esto sigue
	 * aquí y se presentó el trabajo, es porque no alcanzó el tiempo para
	 * implementarlo, la idea de esto es usar una sola lista para atracciones y
	 * promociones, ya que ambas heredan de una super clase, hacer las ofertas de
	 * los productos usando el polimorfismo
	 **/

	public static OfertaDAO getOfertaDAO() {
		return new OfertaDAOImplementado();
	}
}
