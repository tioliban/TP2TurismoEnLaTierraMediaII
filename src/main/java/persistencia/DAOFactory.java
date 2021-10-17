package persistencia;

public class DAOFactory {

	public static UsuarioDAO getUserDAO() {
		return new UsuarioDAOImplementado();
	}
}
