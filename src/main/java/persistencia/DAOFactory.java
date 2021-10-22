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
}
