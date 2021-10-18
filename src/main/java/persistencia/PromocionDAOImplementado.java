package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import clases.Promocion;
import clases.TipoAtraccion;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.ExcepcionDeBase;
import excepciones.ExcepcionDePromocion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class PromocionDAOImplementado implements PromocionDAO {

	@Override
	public HashMap<String, Promocion> findAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM promociones");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			HashMap<String, Promocion> promociones = new HashMap<String, Promocion>();
			while (fila.next()) {
				promociones.put(fila.getString(1), this.levantarPromocion(fila));
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
			StringBuilder consultaSQL = new StringBuilder("SELECT count(1) as TOTAL FROM promociones");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante el conteo de las promociones\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Promocion promocionAInsertar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"INSERT into promociones (nombreAtraccion, tiempo, costo, cupo, nombreTipo) VALUES (?, ?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAInsertar.getNombre());
			statement.setDouble(2, promocionAInsertar.getTiempo());
			statement.setDouble(3, promocionAInsertar.getCosto());
			statement.setInt(4, promocionAInsertar.getCupo());
			statement.setString(5, promocionAInsertar.getTipo().name());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la inserción de la atraccion: \"");
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
			StringBuilder consultaSQL = new StringBuilder("UPDATE atraccion SET cupo = ? WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAActualizar.getCupo());
			statement.setString(2, promocionAActualizar.getNombre());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la actualización de la atraccion: \"");
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
			StringBuilder consultaSQL = new StringBuilder("DELETE FROM atracciones WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAEliminar.getNombre());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la eliminación de la atraccion: \"");
			mensaje.append(promocionAEliminar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Promocion findByNombre(String nombre) {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM atracciones WHERE nombreAtraccion = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, nombre);
			ResultSet fila = statement.executeQuery();
			Promocion promocionARetornar = null;
			if (fila.next()) {
				promocionARetornar = this.levantarPromocion(fila);
			}
			return promocionARetornar;
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

	private Promocion levantarPromocion(ResultSet fila) throws ExcepcionDeBase, ExcepcionDePromocion, SQLException {
		return new Promocion(fila.getString(1), fila.getDouble(2), fila.getDouble(3),
				TipoAtraccion.valueOf(fila.getString(5)), fila.getInt(4));
	}
}
