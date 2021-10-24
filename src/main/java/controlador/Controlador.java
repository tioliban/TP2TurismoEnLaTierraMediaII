package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Controlador {
	private static final String DIRECCION_DE_LA_BASE_DE_DATOS = "jdbc:sqlite:bd.db";
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(DIRECCION_DE_LA_BASE_DE_DATOS);
		}
		return connection;
	}
	/**
	 * Acá tengo que poner el controlamiento del sistema y los estados del sistema,
	 * la interacción con el usuario lo tengo que poner en otro lado
	 */
}
