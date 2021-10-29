package controlador;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.DriverManager;

import clases.Promocion;
import clases.SugerirProducto;
import clases.Usuario;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;
import persistencia.DAOFactory;

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
	public static void iniciarSistema() {
		try {
			// Aca deben ir las salidas por pantalla que son la interaccion con el usuario
			SugerirProducto ofertas = new SugerirProducto(DAOFactory.getUsuarioDAO().findAll(),
					DAOFactory.getPromocionDAO().findAll(), DAOFactory.getAtraccionDAO().findAll());
			for (Promocion promo : ofertas.getPromociones()) {
				System.out.println(promo);
			}
			for (Usuario usuario : ofertas.getUsuarios()) {
				ofertas.sugerirPromocionConPreferencia(usuario);
			}
			Controlador.guardarSistema();

			while (true) {
				Controlador.reanudarSistema();
			}
		} catch (SelectDataBaseExcepcion e) {
			System.out.println(e);
		} catch (InsertDataBaseExcepcion e) {
			System.out.println(e);
		} catch (UpdateDataBaseExcepcion e) {
			System.out.println(e.getMessage());
		} catch (DeleteDataBaseExcepcion e) {
			System.out.println(e.getMessage());
		}
	}

	public static void reanudarSistema() {

		if (true)
			Controlador.guardarSistema();

	}

	private static void guardarSistema() {
	}
}
