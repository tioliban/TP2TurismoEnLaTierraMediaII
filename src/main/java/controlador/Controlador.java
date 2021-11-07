package controlador;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.HashMap;
import java.sql.DriverManager;

import clases.Ofertador;
import clases.Usuario;
import clases.Atraccion;
import clases.Base;
import clases.Promocion;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;
import persistencia.DAOFactory;

public class Controlador {
	private static HashMap<String, Base> productos;
	private static HashMap<String, Promocion> pro;
	private static HashMap<String, Atraccion> atr;
	private static HashMap<String, Usuario> usu;
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
			mostrarBienvenida();
			levantarInstancias();
			sugerirProductos();
			guardarSistema();
			preguntarInteraccion();
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

	public static void mostrarBienvenida() {
		System.out.println("");
		System.out.println("*************************************************");
		System.out.println("*   Bienvenido al parque de la Tierra Media II  *");
		System.out.println("*************************************************");
		System.out.println("");
		System.out.println("");
	}

	public static void levantarInstancias() {
		usu = new HashMap<String, Usuario>();
		pro = new HashMap<String, Promocion>();
		atr = new HashMap<String, Atraccion>();
		productos = new HashMap<String, Base>();
		pro.putAll(DAOFactory.getPromocionDAO().findAll());
		atr.putAll(DAOFactory.getAtraccionDAO().findAll());
		usu.putAll(DAOFactory.getUsuarioDAO().findAll());
		for (String ids : pro.keySet()) {
			pro.get(ids).setAtracciones(atr);
		}
		productos.putAll(pro);
		productos.putAll(atr);
	}

	public static void sugerirProductos() {
		Ofertador ofertador = new Ofertador(usu, productos);
		ofertador.sugerirLosProductosATodosLosUsarios();
	}

	public static void guardarSistema() {
		for (String id : usu.keySet())
			DAOFactory.getUsuarioDAO().update(usu.get(id));
		for (String id : atr.keySet())
			DAOFactory.getAtraccionDAO().update(atr.get(id));
		for (String id : pro.keySet())
			DAOFactory.getPromocionDAO().update(pro.get(id));
	}

	public static void preguntarInteraccion() {
		boolean algo = true;
		if (algo) {
			// interaccion individual de usuario
			System.out.println();
		} else {
			// volver a ofrecer a todos los usuarios
			System.out.println();
		}
	}

	public static void reanudarSistema() {
		mostrarReanudacion();
		if (true)
			Controlador.guardarSistema();

	}

	public static void mostrarReanudacion() {
		System.out.println("");
		System.out.println("*************************************************");
		System.out.println("*Hola nuevamente al parque de la Tierra Media II*");
		System.out.println("*************************************************");
		System.out.println("");
		System.out.println("");

	}

}
