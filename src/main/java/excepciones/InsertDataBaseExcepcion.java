package excepciones;

public class InsertDataBaseExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915452064156238303L;
	
	public InsertDataBaseExcepcion(String mensaje) {
		super(mensaje);
	}
}
