package excepciones;

public class SelectDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026644645095812244L;

	public SelectDataBaseExcepcion(String entrada) {
		super("Ha ocurrido un error al momento de buscar un" + entrada);
	}
}
