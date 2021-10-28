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
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class PromocionDAOImplementado implements PromocionDAO {

	private final String CONSULTA = "SELECT promociones.idPromocion, promociones.nombrePromocion, tipoAtraccion.nombreTipoAtraccion, promociones.nombreTipoPromocion FROM promociones NATURAL JOIN tipoAtraccion";
	private final String MENSAJE = "a promocion";
	private Connection coneccion;
	private PreparedStatement statement;
	private ResultSet fila;
	private ArrayList<Promocion> promociones;
	private ArrayList<String> atracciones;
	private StringBuilder consultaSQL = new StringBuilder();
	private Promocion promocion;
	private TipoAtraccion tipo;

	public ArrayList<Promocion> findAll() {
		try {
			this.prepararConsulta(CONSULTA, consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			promociones = new ArrayList<Promocion>();
			while (fila.next()) {
				promociones.add(this.levantarPromocion(fila));
			}
			return promociones;
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int countAll() {
		try {
			this.prepararConsulta("SELECT count(1) as TOTAL FROM promociones", consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int insert(Promocion promocionAInsertar) {
		try {
			this.prepararConsulta("INSERT INTO promociones (nombrePromocion, idTipoAtraccion, nombreTipoPromocion)",
					consultaSQL);
			consultaSQL.append(" VALUES (?, ?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAInsertar.getId());
			statement.setInt(2, promocionAInsertar.getTipoAtraccion().ordinal() + 1);
			statement.setString(3, promocionAInsertar.getPromo());
			statement.executeUpdate();
			return this.integridadReferencial(promocionAInsertar);
		} catch (Exception e) {
			throw new InsertDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int update(Promocion promocionAActualizar) {
		try {
			this.prepararConsulta("UPDATE promociones SET nombrePromocion = ?,", consultaSQL);
			consultaSQL.append(" WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAActualizar.getId());
			statement.setInt(2, Integer.parseInt(promocionAActualizar.getId().substring(2)));
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new UpdateDataBaseExcepcion(MENSAJE, e);
		}
	}

	public int delete(Promocion promocionAEliminar) {
		try {
			this.prepararConsulta("DELETE FROM promociones WHERE idPromocion = ?", consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, Integer.parseInt(promocionAEliminar.getId().substring(2)));
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new DeleteDataBaseExcepcion(MENSAJE, e);
		}
	}

	public Promocion findById(int id) {
		try {
			this.prepararConsulta(CONSULTA, consultaSQL);
			consultaSQL.append(" WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			fila = statement.executeQuery();
			fila.next();
			return this.levantarPromocion(fila);
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	private StringBuilder prepararConsulta(String inicio, StringBuilder consulta) throws SQLException {
		coneccion = Controlador.getConnection();
		consulta.setLength(0);
		return consulta.append(inicio);
	}

	private int integridadReferencial(Promocion insercion) throws RuntimeException, SQLException {
		this.prepararConsulta("INSERT INTO promocionesAtracciones (idPromocion, idAtraccion)", consultaSQL);
		consultaSQL.append(" VALUES (?, ?)");
		statement = coneccion.prepareStatement(consultaSQL.toString());
		for (String id : insercion.getAtracciones()) {
			statement.setInt(1, Integer.parseInt(insercion.getId().substring(2)));
			statement.setInt(2, Integer.parseInt(id.substring(2)));
			statement.executeUpdate();
		}
		return this.insertarTipoPromocion(insercion);
	}

	private int insertarTipoPromocion(Promocion insercion) throws SQLException {
		this.prepararConsulta("INSERT INTO promociones", consultaSQL);
		consultaSQL.append(insercion.getPromo());
		if (insercion.getPromo().equals("AxB")) {
			consultaSQL.append("(idPromocion, idAtraccion)");
			consultaSQL.append(" VALUES (?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Testear si puedo poner
			statement.setInt(2, Integer.parseInt(insercion.getAtracciones().get(0).substring(2))); // despues de los if
		} else if (insercion.getPromo().equals("Absoluta")) {
			consultaSQL.append("s (idPromocion, precioFinal)");
			consultaSQL.append(" VALUES (?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
			statement.setDouble(2, insercion.getCosto());
		} else {
			consultaSQL.append("es (idPromocion, porcentaje)");
			consultaSQL.append(" VALUES (?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
			statement.setDouble(2, insercion.getDescuento());
		}
		statement.setInt(1, Integer.parseInt(insercion.getId().substring(2)));
		return statement.executeUpdate();
	}

	private Promocion levantarPromocion(ResultSet filaPromociones) throws SQLException {
		atracciones = new ArrayList<String>();
		this.prepararConsulta("SELECT idAtraccion FROM promocionesAtracciones WHERE idPromocion = ?", consultaSQL);
		statement = coneccion.prepareStatement(consultaSQL.toString());
		statement.setInt(1, filaPromociones.getInt(1));
		ResultSet filaAtracciones = statement.executeQuery();
		while (filaAtracciones.next()) {
			atracciones.add("2." + filaAtracciones.getInt(1));
		}
		this.prepararConsulta("SELECT sum(atracciones.tiempo) as TIEMPO, sum(atracciones.costo) as COSTO", consultaSQL);
		consultaSQL.append(" FROM atracciones NATURAL JOIN promocionesAtracciones NATURAL JOIN promociones");
		consultaSQL.append(" WHERE promociones.nombrePromocion = ?");
		statement = coneccion.prepareStatement(consultaSQL.toString());
		statement.setString(1, filaPromociones.getString(1));
		filaAtracciones = statement.executeQuery();
		if (filaAtracciones.next()) {
			ResultSet costo;
			if (filaPromociones.getString(4).equals("AxB")) {
				this.prepararConsulta("SELECT atracciones.idAtraccion, atracciones.costo", consultaSQL);
				consultaSQL.append(" FROM promocionesAxB NATURAL JOIN atracciones");
				consultaSQL.append(" WHERE promocionesAxB.idPromocion = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				costo = statement.executeQuery();
				if (costo.next())
					tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
				promocion = new AxB(filaPromociones.getInt(1), filaPromociones.getString(2),
						filaAtracciones.getDouble("TIEMPO"), filaAtracciones.getDouble("COSTO") - costo.getDouble(2),
						tipo, atracciones, "2." + costo.getInt(1));
			} else if (filaPromociones.getString(4).equals("Absoluta")) {
				this.prepararConsulta("SELECT precioFinal FROM promocionesAbsolutas", consultaSQL);
				consultaSQL.append(" WHERE idPromocion = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				costo = statement.executeQuery();
				if (costo.next())
					tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
				promocion = new Absoluta(filaPromociones.getInt(1), filaPromociones.getString(2),
						filaAtracciones.getDouble("TIEMPO"), costo.getDouble(1), tipo, atracciones);
			} else {
				this.prepararConsulta("SELECT porcentaje FROM promocionesPorcentuales", consultaSQL);
				consultaSQL.append(" WHERE idPromocion = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				costo = statement.executeQuery();
				tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
				promocion = new Porcentual(filaPromociones.getInt(1), filaPromociones.getString(2),
						filaAtracciones.getDouble("TIEMPO"),
						filaAtracciones.getDouble("COSTO") * (1 - (costo.getDouble(1) / 100)), tipo, atracciones,
						costo.getDouble(1));
			}
		}
		return promocion;
	}
}
