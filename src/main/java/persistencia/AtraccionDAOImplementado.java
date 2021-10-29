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
	private final String MENSAJE = "a atraccion";
	private Connection coneccion;
	private PreparedStatement statement;
	private ResultSet fila;
	private ArrayList<Atraccion> atracciones;
	private StringBuilder consultaSQL = new StringBuilder();
	private Atraccion atraccion;
	private TipoAtraccion tipo;

	public ArrayList<Atraccion> findAll() {
		try {
			this.prepararConsulta(CONSULTA_SELECT, consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			atracciones = new ArrayList<Atraccion>();
			while (fila.next()) {
				atracciones.add(this.levantarAtraccion(fila));
			}
			return atracciones;
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int countAll() {
		try {
			this.prepararConsulta("SELECT count(1) as TOTAL FROM atracciones", consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int insert(Atraccion atraccionAInsertar) {
		try {
			this.prepararConsulta("INSERT INTO atracciones", consultaSQL);
			consultaSQL.append(" (nombreAtraccion, tiempo, costo, cupo, idTipoAtraccion)");
			consultaSQL.append(" VALUES (?, ?, ?, ?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, atraccionAInsertar.getId());
			statement.setDouble(2, atraccionAInsertar.getTiempo());
			statement.setDouble(3, atraccionAInsertar.getCosto());
			statement.setInt(4, atraccionAInsertar.getCupo());
			statement.setInt(5, atraccionAInsertar.getTipoAtraccion().ordinal() + 1);
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new InsertDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int update(Atraccion atraccionAActualizar) {
		try {
			this.prepararConsulta("UPDATE atraccion SET cupo = ? WHERE idAtraccion = ?", consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, atraccionAActualizar.getCupo());
			statement.setInt(2, Integer.parseInt(atraccionAActualizar.getId().substring(2)));
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new UpdateDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int delete(Atraccion atraccionAEliminar) {
		try {
			this.prepararConsulta("DELETE FROM atracciones WHERE idAtraccion = ?", consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, Integer.parseInt(atraccionAEliminar.getId().substring(2)));
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new DeleteDataBaseExcepcion(MENSAJE, e);
		}
	}

	public Atraccion findById(int id) {
		try {
			this.prepararConsulta(CONSULTA_SELECT, consultaSQL);
			consultaSQL.append(" WHERE atracciones.idAtraccion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			fila = statement.executeQuery();
			if (fila.next()) {
				atraccion = this.levantarAtraccion(fila);
			}
			return atraccion;
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	private StringBuilder prepararConsulta(String inicio, StringBuilder consulta) throws SQLException {
		coneccion = Controlador.getConnection();
		consulta.setLength(0);
		return consulta.append(inicio);
	}

	private Atraccion levantarAtraccion(ResultSet fila) throws SQLException {
		tipo = TipoAtraccion.valueOf(fila.getString(6).toUpperCase());
		return new Atraccion(fila.getInt(1), fila.getString(2), fila.getDouble(3), fila.getDouble(4), fila.getInt(5),
				tipo);
	}
}
