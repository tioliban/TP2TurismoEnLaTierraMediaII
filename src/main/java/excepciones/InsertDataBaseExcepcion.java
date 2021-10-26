package excepciones;

public class InsertDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915452064156238303L;
	
	public InsertDataBaseExcepcion(String entrada) {
		super("Ha ocurrido un error al momento de insertar un" + entrada);
	}
}
