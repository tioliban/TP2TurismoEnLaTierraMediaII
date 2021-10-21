package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import clases.Atraccion;
import clases.TipoAtraccion;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.ExcepcionDeAtraccion;
import excepciones.ExcepcionDeBase;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class AtraccionDAOImplementado implements AtraccionDAO {

	@Override
	public HashMap<String, Atraccion> findAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM atracciones");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			HashMap<String, Atraccion> atracciones = new HashMap<String, Atraccion>();
			while (fila.next()) {
				atracciones.put(fila.getString(1), this.levantarAtraccion(fila));
			}
			return atracciones;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la recuperacion de las atracciones\n");
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
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante el conteo de las atracciones\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Atraccion atraccionAInsertar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"INSERT into atracciones (nombreAtraccion, tiempo, costo, cupo, nombreTipo) VALUES (?, ?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, atraccionAInsertar.getNombre());
			statement.setDouble(2, atraccionAInsertar.getTiempo());
			statement.setDouble(3, atraccionAInsertar.getCosto());
			statement.setInt(4, atraccionAInsertar.getCupo());
			statement.setString(5, atraccionAInsertar.getTipoAtraccion().name());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la inserción de la atraccion: \"");
			mensaje.append(atraccionAInsertar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Atraccion atraccionAActualizar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("UPDATE atraccion SET cupo = ? WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, atraccionAActualizar.getCupo());
			statement.setString(2, atraccionAActualizar.getNombre());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la actualización de la atraccion: \"");
			mensaje.append(atraccionAActualizar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new UpdateDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int delete(Atraccion atraccionAEliminar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("DELETE FROM atracciones WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, atraccionAEliminar.getNombre());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la eliminación de la atraccion: \"");
			mensaje.append(atraccionAEliminar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Atraccion findByNombre(String nombre) {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM atracciones WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, nombre);
			ResultSet fila = statement.executeQuery();
			Atraccion atraccionARetornar = null;
			if (fila.next()) {
				atraccionARetornar = this.levantarAtraccion(fila);
			}
			return atraccionARetornar;
		} catch (ExcepcionDeAtraccion excepcion) {
			System.out.println();
			return null;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la búsqueda de la atraccion: \"");
			mensaje.append(nombre);
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Atraccion levantarAtraccion(ResultSet fila) throws ExcepcionDeBase, ExcepcionDeAtraccion, SQLException {
		return new Atraccion(fila.getString(1), fila.getDouble(2), fila.getDouble(3),
				fila.getInt(4), TipoAtraccion.valueOf(fila.getString(5)));
	}
}
