package excepciones;

public class DeleteDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026644645095812244L;

	public DeleteDataBaseExcepcion(String entrada, Exception e) {
		super("Ha ocurrido un error al momento de eliminar un" + entrada, e);
	}
}
