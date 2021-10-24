package persistencia;

import static org.junit.Assert.assertArrayEquals;

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

	private Connection coneccion;
	private StringBuilder consultaSQL = new StringBuilder(), mensaje = new StringBuilder();
	private ArrayList<Promocion> promociones = new ArrayList<Promocion>();
	private PreparedStatement statement;
	private ResultSet fila;
	private ArrayList<Integer> idAtraccion = new ArrayList<Integer>(), idPromocionAtraccion = new ArrayList<Integer>();

	@Override
	public ArrayList<Promocion> findAll() {
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT * FROM promociones");
			coneccion = Controlador.getConnection();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			promociones.clear();
			while (fila.next()) {
				promociones.add(this.levantarPromocion(fila));
			}
			return promociones;
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante la recuperacion de las promociones\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int countAll() {
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(1) as TOTAL FROM promociones");
			coneccion = Controlador.getConnection();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante el conteo de las promociones:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Promocion promocionAInsertar) {
		try {
			consultaSQL.setLength(0);
			Connection coneccion = Controlador.getConnection();
			consultaSQL.append("SELECT idAtraccion FROM atracciones");
			consultaSQL.append(" WHERE nombreAtraccion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			idAtraccion.clear();
			for (String atraccion : promocionAInsertar.getNombresDeAtracciones()) {
				statement.setString(1, atraccion);
				fila = statement.executeQuery();
				if (fila.next()) {
					idAtraccion.add(fila.getInt(1));
				}
			}
			consultaSQL.setLength(0);
			consultaSQL.append("INSERT INTO promocionesAtracciones (idPromocion, idAtraccion)");
			consultaSQL.append(" VALUES (?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			for (Integer id : idAtraccion) {
				statement.setInt(1, promocionAInsertar.getId());
				statement.setInt(2, id);
				statement.executeUpdate();
			}
			consultaSQL.setLength(0);
			consultaSQL.append("INSERT INTO promociones (nombrePromocion, idTipoAtraccion, nombreTipoPromocion)");
			consultaSQL.append(" VALUES (?, ?, ?)");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAInsertar.getNombre());
			statement.setInt(2, promocionAInsertar.getTipoAtraccion().ordinal() + 1);
			statement.setString(3, promocionAInsertar.getPromo());
			statement.executeUpdate();
			consultaSQL.setLength(0);
			consultaSQL.append("INSERT INTO promociones");
			consultaSQL.append(promocionAInsertar.getPromo());
			if (promocionAInsertar.getPromo().equals("AxB")) {
				consultaSQL.append("(idPromocion, idAtraccion)");
				consultaSQL.append(" VALUES (?, ?)");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Testear si puedo poner
				statement.setInt(2, idAtraccion.get(0)); // despues de los if
			} else if (promocionAInsertar.getPromo().equals("Absoluta")) {
				consultaSQL.append("s (idPromocion, precioFinal)");
				consultaSQL.append(" VALUES (?, ?)");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
				statement.setDouble(2, promocionAInsertar.getCosto());
			} else {
				consultaSQL.append("es (idPromocion, porcentaje)");
				consultaSQL.append(" VALUES (?, ?)");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
				statement.setDouble(2, promocionAInsertar.getDescuento());
			}
			statement.setInt(1, promocionAInsertar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante la inserción de una promocion:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Promocion promocionAActualizar) {
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("UPDATE promociones SET nombrePromocion = ?, idTipoAtraccion = ?,");
			consultaSQL.append(" nombreTipoAtraccion = ? WHERE idPromocion = ?");
			coneccion = Controlador.getConnection();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, promocionAActualizar.getNombre());
			statement.setInt(2, promocionAActualizar.getTipoAtraccion().ordinal() + 1);
			statement.setString(3, promocionAActualizar.getPromo());
			statement.setInt(4, promocionAActualizar.getId());
			statement.executeUpdate();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT idAtraccion FROM atracciones");
			consultaSQL.append(" WHERE nombreAtraccion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			idAtraccion.clear();
			for (String atraccion : promocionAActualizar.getNombresDeAtracciones()) {
				statement.setString(1, atraccion);
				fila = statement.executeQuery();
				if (fila.next()) {
					idAtraccion.add(fila.getInt(1));
				}
			}
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT idPromocionesAtracciones FROM promocionesAtracciones");
			consultaSQL.append(" WHERE idPromocion = ? AND idAtraccion = ?");
			idPromocionAtraccion.clear();
			for (Integer id : idAtraccion) {
				statement.setInt(1, promocionAActualizar.getId());
				statement.setInt(2, id);
				fila = statement.executeQuery();
				if (fila.next()) {
					idPromocionAtraccion.add(fila.getInt(1));
				}
			}
			consultaSQL.setLength(0);
			consultaSQL.append("UPDATE promocionesAtracciones SET idPromocion = ?, idAtraccion = ?");
			consultaSQL.append(" WHERE idPromocionesAtracciones = ?");
			for (int indice = 0; indice < idPromocionAtraccion.size(); indice++) {
				statement.setInt(1, promocionAActualizar.getId());
				statement.setInt(2, idAtraccion.get(indice));
				statement.setInt(3, idPromocionAtraccion.get(indice));
				statement.executeUpdate();
			}
			consultaSQL.setLength(0);
			consultaSQL.append("UPDATE promociones");
			consultaSQL.append(promocionAActualizar.getPromo());
			if (promocionAActualizar.getPromo().equals("AxB")) {
				consultaSQL.append(" SET idPromocion = ?, idAtraccion = ?");
				consultaSQL.append(" WHERE idPromocionAxB = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Testear si puedo poner
				statement.setInt(2, idAtraccion.get(0)); // despues de los if
			} else if (promocionAActualizar.getPromo().equals("Absoluta")) {
				consultaSQL.append("s SET idPromocion = ?, precioFinal = ?");
				consultaSQL.append(" WHERE idPromocionAbsolutas = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
				statement.setDouble(2, promocionAActualizar.getCosto());
			} else {
				consultaSQL.append("es SET idPromocion = ?, porcentaje = ?");
				consultaSQL.append(" WHERE idPromocionPorcentuales = ?");
				statement = coneccion.prepareStatement(consultaSQL.toString()); // Esto
				statement.setDouble(2, promocionAActualizar.getDescuento());
			}
			statement.setInt(1, promocionAActualizar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante la actualización de una promocion:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new UpdateDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int delete(Promocion promocionAEliminar) {
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("DELETE FROM promociones WHERE idPromocion = ?");
			coneccion = Controlador.getConnection();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAEliminar.getId());
			statement.executeUpdate();
			consultaSQL.setLength(0);
			consultaSQL.append("DELETE FROM promocionesAtracciones WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAEliminar.getId());
			consultaSQL.setLength(0);
			statement.executeUpdate();
			consultaSQL.append("DELETE FROM promocionesAxB WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAEliminar.getId());
			statement.executeUpdate();
			consultaSQL.setLength(0);
			consultaSQL.append("DELETE FROM promocionesAbsolutas WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAEliminar.getId());
			statement.executeUpdate();
			consultaSQL.setLength(0);
			consultaSQL.append("DELETE FROM promocionesPorcentuales WHERE idPromocion = ?");
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, promocionAEliminar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante la eliminación de una promocion:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Promocion findById(int id) {
		try {
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT * FROM promociones WHERE idPromocion = ?");
			coneccion = Controlador.getConnection();
			statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			fila = statement.executeQuery();
			Promocion promocionARetornar = null;
			if (fila.next()) {
				promocionARetornar = this.levantarPromocion(fila);
			}
			return promocionARetornar;
		} catch (Exception e) {
			mensaje.setLength(0);
			mensaje.append("Ha ocurrido un error durante la búsqueda de una atraccion:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Promocion levantarPromocion(ResultSet filaPromociones) throws SQLException {
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
			promocion = new Porcentual(filaPromociones.getInt(1), filaPromociones.getString(2),
					tiempoYCosto.getDouble(1), tiempoYCosto.getDouble(2) * (1 - (filaDePromociones.getDouble(4) / 100)),
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
