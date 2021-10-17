package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.lang.StringBuilder;

import clases.TipoAtraccion;
import clases.Usuario;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.ExcepcionDeUsuario;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class UsuarioDAOImplementado implements UsuarioDAO {

	@Override
	public HashMap<String, Usuario> findAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM usuarios");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
			while(fila.next()) {
				usuarios.put(fila.getString(1), this.levantarUsuario(fila));
			}
			return usuarios;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante el conteo de los usuarios\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int countAll() {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT count(1) as TOTAL FROM usuarios");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			ResultSet fila = statement.executeQuery();
			fila.next();
			return fila.getInt("TOTAL");
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante el conteo de los usuarios\n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int insert(Usuario usuarioAInsertar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"INSERT into usuarios (nombreUsuario, tiempo, presupuesto, nombreTipo) VALUES (?, ?, ?, ?)");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, usuarioAInsertar.getNombre());
			statement.setDouble(2, usuarioAInsertar.getTiempo());
			statement.setDouble(3, usuarioAInsertar.getPresupuesto());
			statement.setString(4, usuarioAInsertar.getPreferencia().name());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la inserción del usuario: \"");
			mensaje.append(usuarioAInsertar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new InsertDataBaseExcepcion(mensaje.toString());
		}
	}

	@Override
	public int update(Usuario usuarioAActualizar) {
		try {
			StringBuilder consultaSQL = new StringBuilder(
					"UPDATE usuarios SET tiempo = ?, presupuesto = ? WHERE nombreUsuario = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setDouble(1, usuarioAActualizar.getTiempo());
			statement.setDouble(2, usuarioAActualizar.getPresupuesto());
			statement.setString(3, usuarioAActualizar.getNombre());
			int filas = statement.executeUpdate();
			return filas;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la actualización del usuario: \"");
			mensaje.append(usuarioAActualizar.getNombre());
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
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
			int filas = statement.executeUpdate();
			return filas;
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
	public Usuario findByNombre(String nombre) {
		try {
			StringBuilder consultaSQL = new StringBuilder("SELECT * FROM usuarios WHERE nombreUsuario = ?");
			Connection coneccion = Controlador.getConnection();
			PreparedStatement statement = coneccion.prepareStatement(consultaSQL.toString());
			statement.setString(1, nombre);
			ResultSet fila = statement.executeQuery();
			Usuario usuarioARetornar = null;
			if (fila.next()) {
				usuarioARetornar = this.levantarUsuario(fila);
			}
			return usuarioARetornar;
		} catch (ExcepcionDeUsuario excepcion) {
			System.out.println();
			return null;
		} catch (Exception e) {
			StringBuilder mensaje = new StringBuilder("Ha ocurrido un error durante la búsqueda del usuario: \"");
			mensaje.append(nombre);
			mensaje.append("\". \n");
			mensaje.append("La información de error obtenida es: \n");
			mensaje.append(e.getMessage());
			throw new SelectDataBaseExcepcion(mensaje.toString());
		}
	}

	private Usuario levantarUsuario(ResultSet fila) throws SQLException, ExcepcionDeUsuario {
		return new Usuario(fila.getString(1), fila.getDouble(2), fila.getDouble(3),
				TipoAtraccion.valueOf(fila.getString(4)));
	}
}
