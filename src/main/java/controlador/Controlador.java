package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Controlador {
	private static String direccionDeBaseDeDatos = "jdbc:sqlite:bd.db";
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(direccionDeBaseDeDatos);
		}
		return connection;
	}
	/**
	 * Acá tengo que poner el controlamiento del sistema y los estados del sistema,
	 * la interacción con el usuario lo tengo que poner en otro lado
	 */
}
