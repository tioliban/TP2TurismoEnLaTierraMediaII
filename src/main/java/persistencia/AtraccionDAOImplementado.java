package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import clases.Atraccion;
import clases.TipoAtraccion;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class AtraccionDAOImplementado implements AtraccionDAO {

	private final String CONSULTA_SELECT = "SELECT atracciones.idAtraccion, atracciones.nombreAtraccion, atracciones.tiempo, atracciones.costo, atracciones.cupo, tipoAtraccion.nombreTipoAtraccion FROM atracciones NATURAL JOIN tipoAtraccion";

	@Override
	public ArrayList<Atraccion> findAll() {
		try {
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(CONSULTA_SELECT);
			ResultSet fila = statement.executeQuery();
			ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
			while (fila.next()) {
				atracciones.add(this.levantarAtraccion(fila));
			}
			return atracciones;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la recuperacion de las atracciones:\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int countAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT count(1) as TOTAL FROM atracciones");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante el conteo de las atracciones:\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Atraccion atraccionAInsertar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("INSERT INTO atracciones");
			consultaSQL.append(" (nombreAtraccion, tiempo, costo, cupo, idTipoAtraccion)");
			consultaSQL.append(" VALUES (?, ?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, atraccionAInsertar.getNombre());
			statement.setDouble(2, atraccionAInsertar.getTiempo());
			statement.setDouble(3, atraccionAInsertar.getCosto());
			statement.setInt(4, atraccionAInsertar.getCupo());
			statement.setInt(5, atraccionAInsertar.getTipoAtraccion().ordinal() + 1);
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la inserción de una atraccion:\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Atraccion atraccionAActualizar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("UPDATE atraccion SET");
			consultaSQL.append(" cupo = ? WHERE idAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, atraccionAActualizar.getCupo());
			statement.setInt(2, atraccionAActualizar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la actualización de una atraccion:\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new UpdateDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int delete(Atraccion atraccionAEliminar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("DELETE FROM atracciones");
			consultaSQL.append(" WHERE idAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, atraccionAEliminar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la eliminación de una atraccion:\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Atraccion findById(int id) {
		try {
			StringBuilder consultaSQL = new StringBuilder(CONSULTA_SELECT);
			consultaSQL.append(" WHERE atracciones.idAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			ResultSet fila = statement.executeQuery();
			Atraccion atraccionARetornar = null;
			if (fila.next()) {
				atraccionARetornar = this.levantarAtraccion(fila);
			}
			return atraccionARetornar;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la búsqueda de una atraccion:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Atraccion levantarAtraccion(ResultSet fila) throws SQLException {
		return new Atraccion(fila.getInt(1), fila.getString(2), fila.getDouble(3), fila.getDouble(4),
				fila.getInt(5),	TipoAtraccion.valueOf(fila.getString(6)));
	}
}
