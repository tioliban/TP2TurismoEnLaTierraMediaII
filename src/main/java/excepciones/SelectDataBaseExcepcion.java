package excepciones;

public class SelectDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026644645095812244L;

	public SelectDataBaseExcepcion(String mensaje) {
		super(mensaje);
	}
}
