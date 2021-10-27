package excepciones;

public class UpdateDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915452064156238303L;
	
	public UpdateDataBaseExcepcion(String entrada, Exception e) {
		super("Ha ocurrido un error al momento de actualizar un" + entrada, e);
	}
}
