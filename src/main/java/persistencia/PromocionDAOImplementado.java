package persistencia;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import clases.Absoluta;
import clases.Atraccion;
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
	private final String PRIMARY_KEY = " WHERE idPromocion = ?";
	private final String VALUES = " VALUES (?, ?)";
	private final String MENSAJE = "a promocion";
	private Connection coneccion;
	private PreparedStatement statement;
	private ResultSet fila;
	private StringBuilder consultaSQL = new StringBuilder();

	public HashMap<String, Promocion> findAll() {
		try {
			this.prepararConsulta(CONSULTA, consultaSQL);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			HashMap<String, Promocion> promociones = new HashMap<String, Promocion>();
			while (fila.next()) {
				promociones.put("1." + fila.getString(1), this.levantarPromocion(fila));
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
			statement.setString(1, promocionAInsertar.getNombre());
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
			consultaSQL.append(PRIMARY_KEY);
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAActualizar.getNombre());
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
			consultaSQL.append(PRIMARY_KEY);
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

	private int integridadReferencial(Promocion insercion) throws SQLException {
		this.prepararConsulta("INSERT INTO promocionesAtracciones (idPromocion, idAtraccion)", consultaSQL);
		consultaSQL.append(VALUES);
		statement = coneccion.prepareStatement(consultaSQL.toString());
		for (String id : insercion.getAtracciones().keySet()) {
			statement.setInt(1, Integer.parseInt(insercion.getId().substring(2)));
			statement.setInt(2, Integer.parseInt(insercion.getAtracciones().get(id).getId().substring(2)));
			statement.executeUpdate();
		}
		return this.insertarTipoPromocion(insercion);
	}

	private int insertarTipoPromocion(Promocion insercion) throws SQLException {
		this.prepararConsulta("INSERT INTO promociones", consultaSQL);
		consultaSQL.append(insercion.getPromo());
		if (insercion.getPromo().equals("AxB")) {
			consultaSQL.append("(idPromocion, idAtraccion)");
			consultaSQL.append(VALUES);
			AxB promo = (AxB) insercion;
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Testear si puedo poner
			statement.setInt(2, Integer.parseInt(promo.getAtraccionGratis().substring(2))); // despues de los if
		} else if (insercion.getPromo().equals("Absoluta")) {
			consultaSQL.append("s (idPromocion, precioFinal)");
			consultaSQL.append(VALUES);
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
			statement.setDouble(2, insercion.getCosto());
		} else {
			consultaSQL.append("es (idPromocion, porcentaje)");
			consultaSQL.append(VALUES);
			statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
			statement.setDouble(2, insercion.getDescuento());
		}
		statement.setInt(1, Integer.parseInt(insercion.getId().substring(2)));
		return statement.executeUpdate();
	}

	private Promocion levantarPromocion(ResultSet filaPromociones) throws SQLException {
		HashMap<String, Atraccion> idAtracciones = new HashMap<String, Atraccion>();
		double tiempo = 0, costo = 0;
		Promocion promocion = null;
		TipoAtraccion tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
		this.prepararConsulta("SELECT promocionesAtracciones.idAtraccion, atracciones.tiempo, atracciones.costo",
				consultaSQL);
		consultaSQL.append(" FROM promocionesAtracciones NATURAL JOIN atracciones");
		consultaSQL.append(PRIMARY_KEY);
		statement = coneccion.prepareStatement(consultaSQL.toString());
		statement.setInt(1, filaPromociones.getInt(1));
		ResultSet filaAtracciones = statement.executeQuery();
		while (filaAtracciones.next()) {
			idAtracciones.put("2." + filaAtracciones.getInt(1), null);
			tiempo += filaAtracciones.getDouble(2);
			costo += filaAtracciones.getDouble(3);
		}
		if (filaAtracciones.next()) {
			ResultSet consulta;
			if (filaPromociones.getString(4).equals("AxB")) {
				this.prepararConsulta("SELECT atracciones.idAtraccion, atracciones.costo", consultaSQL);
				consultaSQL.append(" FROM promocionesAxB NATURAL JOIN atracciones");
				consultaSQL.append(" WHERE promocionesAxB.idPromocion = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				consulta = statement.executeQuery();
				if (consulta.next())
					promocion = new AxB(filaPromociones.getInt(1), filaPromociones.getString(2), tiempo,
							costo - consulta.getDouble(2), tipo, idAtracciones, "2." + consulta.getInt(1));
			} else if (filaPromociones.getString(4).equals("Absoluta")) {
				this.prepararConsulta("SELECT precioFinal FROM promocionesAbsolutas", consultaSQL);
				consultaSQL.append(PRIMARY_KEY);
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				consulta = statement.executeQuery();
				if (consulta.next())
					tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
				promocion = new Absoluta(filaPromociones.getInt(1), filaPromociones.getString(2), tiempo,
						consulta.getDouble(1), tipo, idAtracciones);
			} else {
				this.prepararConsulta("SELECT porcentaje FROM promocionesPorcentuales", consultaSQL);
				consultaSQL.append(PRIMARY_KEY);
				statement = coneccion.prepareStatement(consultaSQL.toString());
				statement.setInt(1, filaPromociones.getInt(1));
				consulta = statement.executeQuery();
				tipo = TipoAtraccion.valueOf(filaPromociones.getString(3).toUpperCase());
				promocion = new Porcentual(filaPromociones.getInt(1), filaPromociones.getString(2), tiempo,
						costo * (1 - (consulta.getDouble(1) / 100)), tipo, idAtracciones, consulta.getDouble(1));
			}
		}
		return promocion;
	}
}
