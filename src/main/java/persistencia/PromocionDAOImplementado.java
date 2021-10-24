package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import clases.Absoluta;
import clases.AxB;
import clases.Porcentual;
import clases.Promocion;
import clases.TipoAtraccion;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class PromocionDAOImplementado implements PromocionDAO {

	private final String SELECT_TODOS = "SELECT promociones.idPromocion, promociones.nombrePromocion, promociones.tiempo, usuarios.presupuesto, tipoAtraccion.nombreTipoAtraccion FROM usuarios NATURAL JOIN tipoAtraccion";
	private final String SELECT_ITINERARIOS_PROMOCIONES = "SELECT itinerarioPromociones.idPromocion FROM usuarios NATURAL JOIN itinerarioPromociones";
	private final String SELECT_ITINERARIOS_ATRACCIONES = "SELECT itinerarioAtracciones.idAtraccion FROM usuarios NATURAL JOIN itinerarioAtracciones";

	@Override
	public ArrayList<Promocion> findAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM promociones");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			ArrayList<Promocion> promociones = new ArrayList<Promocion>();
			while (fila.next()) {
				promociones.add(this.levantarPromocion(fila));
			}
			return promociones;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la recuperacion de las promociones\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int countAll() {
		try {
			String consultaSQL = "SELECT count(1) as TOTAL FROM promociones";
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL);
			ResultSet fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante el conteo de las promociones:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Promocion promocionAInsertar) {
		try {
			StringBuilder insertSQL = new StringBuilder(
					"INSERT into promociones (nombrePromocion, tipo, precioFinal, descuento) VALUES (?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			StringBuilder consultaSQL = new StringBuilder("SELECT atracciones.nombreAtraccion, atracciones.costo");
			consultaSQL.append(" FROM atracciones NATURAL JOIN promocionesAtracciones NATURAL JOIN promociones");
			consultaSQL.append(" WHERE promocion.nombrePromocion = ?");
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAInsertar.getNombre());
			ResultSet fila = statement.executeQuery();
			consultaSQL = new StringBuilder("INSERT into promocionesAtracciones (nombrePromocion, nombreAtraccion)");
			consultaSQL.append(" VALUES (?, ?)");
			HashMap<String, Double> atracciones = new HashMap<String, Double>();
			ArrayList<String> indice = new ArrayList<String>();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			while (fila.next()) {
				atracciones.put(fila.getString(1), fila.getDouble(2));
				indice.add(fila.getString(1));
			}
			for(String indic:indice) {
				statement.setString(1, promocionAInsertar.getNombre());
				statement.setString(2, indic);
				statement.executeUpdate();
			}
			statement = coneccion.prepareStatement(insertSQL.toString());
			statement.setString(1, promocionAInsertar.getNombre());
			statement.setString(2, promocionAInsertar.getPromo());
			if (promocionAInsertar.getPromo().equals("AxB")) {
				statement.setDouble(3, atracciones.get(indice.get(0)));
				statement.setDouble(4, 0);
			} else if (promocionAInsertar.getPromo().equals("Absoluta")) {
				statement.setDouble(3, promocionAInsertar.getCosto());
				statement.setDouble(4, 0);
			} else {
				statement.setDouble(3, 0);
				statement.setDouble(4, promocionAInsertar.getDescuento());
			}
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la inserción de la promocion: \"");
			mensaje.append(promocionAInsertar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Promocion promocionAActualizar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"UPDATE promociones SET tipo = ?, precioFinal = ?, descuento = ? WHERE nombrePromocion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAActualizar.getPromo());
			if (promocionAActualizar.getPromo().equals("AxB")) {
				statement.setDouble(2, -1);
				statement.setDouble(3, -1);
			} else if (promocionAActualizar.getPromo().equals("Absoluta")) {
				statement.setDouble(2, promocionAActualizar.getCosto());
				statement.setDouble(3, -1);
			} else {
				statement.setDouble(2, -1);
				statement.setDouble(3, promocionAActualizar.getDescuento());
			}
			statement.setString(4, promocionAActualizar.getNombre());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la actualización de la promocion: \"");
			mensaje.append(promocionAActualizar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new UpdateDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int delete(Promocion promocionAEliminar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("DELETE FROM promociones WHERE nombrePromocion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAEliminar.getNombre());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la eliminación de la promocion: \"");
			mensaje.append(promocionAEliminar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Promocion findById(int id) {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM promociones WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			ResultSet fila = statement.executeQuery();
			Promocion promocionARetornar = null;
			if (fila.next()) {
				promocionARetornar = this.levantarPromocion(fila);
			}
			return promocionARetornar;
		} catch (ExcepcionDePromocion excepcion) {
			System.out.println();
			return null;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la búsqueda de la atraccion: \"");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Promocion levantarPromocion(ResultSet filaPromociones) throws SQLException{
		Promocion promocion = null;
		ArrayList<String> nombresDeAtracciones = new ArrayList<String>();
		StringBuilder nombresDeAtraccionesSQL = new StringBuilder(
				"SELECT nombreAtraccion FROM promocionesAtracciones WHERE nombrePromocion = ?");
		StringBuilder tiempoYCostoSQL = new StringBuilder("SELECT sum(atracciones.tiempo), sum(atracciones.costo)");
		tiempoYCostoSQL.append(" FROM atracciones NATURAL JOIN promocionesAtracciones NATURAL JOIN promociones");
		tiempoYCostoSQL.append(" Where promociones.nombrePromocion = ?");
		Connection coneccion = Controlador.getConnection();
		PreparedStatement statement = coneccion.prepareStatement(nombresDeAtraccionesSQL.toString());
		statement.setString(1, filaPromociones.getString(1));
		ResultSet filaDePromocionesAtracciones = statement.executeQuery();
		statement = coneccion.prepareStatement(tiempoYCostoSQL.toString());
		statement.setString(1, filaPromociones.getString(1));
		ResultSet tiempoYCosto = statement.executeQuery();
		while (filaDePromocionesAtracciones.next()) {
			nombresDeAtracciones.add(filaDePromocionesAtracciones.getString(1));
		}
		switch (filaPromociones.getString(2)) {
			case "Porcentual": {
				promocion = new Porcentual(filaPromociones.getInt(1),filaPromociones.getString(2), tiempoYCosto.getDouble(1),
						tiempoYCosto.getDouble(2) * (1 - (filaDePromociones.getDouble(4) / 100)),
						TipoAtraccion.valueOf(filaDePromociones.getString(4)), nombresDeAtracciones,
						filaDePromociones.getDouble(4));
				break;
			}
			case "AxB": {
				promocion = new AxB(filaDePromociones.getString(1), tiempoYCosto.getDouble(1),
						tiempoYCosto.getDouble(2) - filaDePromociones.getDouble(3),
						TipoAtraccion.valueOf(filaDePromociones.getString(4)), nombresDeAtracciones,
						filaDePromociones.getString(4));
				break;
			}
			case "Absoluta": {
				promocion = new Absoluta(filaDePromociones.getString(1), tiempoYCosto.getDouble(1),
						filaDePromociones.getDouble(3), TipoAtraccion.valueOf(filaDePromociones.getString(4)),
						nombresDeAtracciones);
				break;
			}
		}
		return promocion;
	}
}
