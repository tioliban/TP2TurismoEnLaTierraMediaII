package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.StringBuilder;

import clases.TipoAtraccion;
import clases.Usuario;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class UsuarioDAOImplementado implements UsuarioDAO {

	private final String SELECT_TODOS = "SELECT usuarios.idUsuario, usuarios.nombreUsuario, usuarios.tiempo, usuarios.presupuesto, tipoAtraccion.nombreTipoAtraccion FROM usuarios NATURAL JOIN tipoAtraccion";
	private final String SELECT_ITINERARIOS_PROMOCIONES = "SELECT itinerarioPromociones.idPromocion FROM usuarios NATURAL JOIN itinerarioPromociones";
	private final String SELECT_ITINERARIOS_ATRACCIONES = "SELECT itinerarioAtracciones.idAtraccion FROM usuarios NATURAL JOIN itinerarioAtracciones";

	@Override
	public ArrayList<Usuario> findAll() {
		try {
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(SELECT_TODOS);
			ResultSet filaUsuario = statement.executeQuery();
			StringBuilder sqlPro = new StringBuilder(SELECT_ITINERARIOS_PROMOCIONES);
			ResultSet filaItinerarioPromociones;
			StringBuilder sqlAtr = new StringBuilder(SELECT_ITINERARIOS_ATRACCIONES);
			ResultSet filaItinerarioAtracciones;
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			StringBuilder key = new StringBuilder();
			while (filaUsuario.next()) {
				key.append("usu.");
				key.append(filaUsuario.getString(2));
				sqlPro.append(" WHERE usuarios.idUsuario = ?");
				statement = coneccion.prepareStatement(sqlPro.toString());
				statement.setInt(1, filaUsuario.getInt(1));
				filaItinerarioPromociones = statement.executeQuery();
				sqlAtr.append(" WHERE usuarios.idUsuario = ?");
				statement = coneccion.prepareStatement(sqlAtr.toString());
				statement.setInt(1, filaUsuario.getInt(1));
				filaItinerarioAtracciones = statement.executeQuery();
				usuarios.add(this.levantarUsuario(filaUsuario, filaItinerarioPromociones, filaItinerarioAtracciones));
			}
			return usuarios;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder(
					"Ha ocurrido un error durante la recuperacion de los usuarios:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int countAll() {
		try {
			String consultaSQL = "SELECT count(1) as TOTAL FROM usuarios";
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL);
			ResultSet fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante el conteo de los usuarios:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Usuario usuarioAInsertar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("INSERT into usuarios (nombreUsuario, tiempo,");
			consultaSQL.append(" presupuesto, nombreTipo) VALUES (?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, usuarioAInsertar.getNombre());
			statement.setDouble(2, usuarioAInsertar.getTiempo());
			statement.setDouble(3, usuarioAInsertar.getPresupuesto());
			statement.setString(4, usuarioAInsertar.getPreferencia().name());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la inserción de un usuario:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Usuario usuarioAActualizar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"UPDATE usuarios SET tiempo = ?, presupuesto = ? WHERE idUsuario = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setDouble(1, usuarioAActualizar.getTiempo());
			statement.setDouble(2, usuarioAActualizar.getPresupuesto());
			statement.setInt(3, usuarioAActualizar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la actualización de un usuario:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new UpdateDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int delete(Usuario usuarioAEliminar) {
		try {
			StringBuilder consultaSQL = new StringBuilder("DELETE FROM usuarios WHERE nombreUsuario = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, usuarioAEliminar.getNombre());
			return statement.executeUpdate();
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la eliminación del usuario: \"");
			mensaje.append(usuarioAEliminar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new DeleteDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public Usuario findById(int id) {
		try {
			StringBuilder consultaSQL = new StringBuilder(SELECT_TODOS);
			consultaSQL.append("WHERE idUsuario = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setInt(1, id);
			ResultSet filaUsuario = statement.executeQuery();
			StringBuilder sqlPro = new StringBuilder(SELECT_ITINERARIOS_PROMOCIONES);
			ResultSet filaItinerarioPromociones;
			StringBuilder sqlAtr = new StringBuilder(SELECT_ITINERARIOS_ATRACCIONES);
			ResultSet filaItinerarioAtracciones;
			sqlPro.append(" WHERE usuarios.idUsuario = ?");
			statement = coneccion.prepareStatement(sqlPro.toString());
			statement.setInt(1, id);
			filaItinerarioPromociones = statement.executeQuery();
			sqlAtr.append(" WHERE usuarios.idUsuario = ?");
			statement = coneccion.prepareStatement(sqlAtr.toString());
			statement.setInt(1, id);
			filaItinerarioAtracciones = statement.executeQuery();
			Usuario usuarioARetornar = null;
			if (filaUsuario.next()) {
				usuarioARetornar = this.levantarUsuario(filaUsuario, filaItinerarioPromociones,
						filaItinerarioAtracciones);
			}
			return usuarioARetornar;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la búsqueda del usuario:\n");
			mensaje.append("La información de error obtenida es:\n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Usuario levantarUsuario(ResultSet filaUsuario, ResultSet filaItinerarioPromociones,
			ResultSet filaItinerarioAtracciones) throws SQLException {
		return new Usuario(filaUsuario.getInt(1), filaUsuario.getString(2), filaUsuario.getDouble(3),
				filaUsuario.getDouble(4), TipoAtraccion.valueOf(filaUsuario.getString(5)));
	}
}
