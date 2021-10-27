package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.StringBuilder;

import clases.Base;
import clases.TipoAtraccion;
import clases.Usuario;
import controlador.Controlador;
import excepciones.DeleteDataBaseExcepcion;
import excepciones.InsertDataBaseExcepcion;
import excepciones.SelectDataBaseExcepcion;
import excepciones.UpdateDataBaseExcepcion;

public class UsuarioDAOImplementado implements UsuarioDAO {

	private final String SELECT_TODOS = "SELECT usuarios.idUsuario, usuarios.nombreUsuario, usuarios.tiempo, usuarios.presupuesto, tipoAtraccion.nombreTipoAtraccion FROM usuarios NATURAL JOIN tipoAtraccion";
	private final String MENSAJE = " usuario";
	private Connection coneccion;
	private PreparedStatement statement;
	private ResultSet filaUsuario;
	private ArrayList<Usuario> usuarios;
	private ArrayList<String> ids;
	private StringBuilder sqlPro = new StringBuilder();
	private Usuario usuario;
	private TipoAtraccion tipo;

	@Override
	public ArrayList<Usuario> findAll() {
		try {
			this.prepararConsulta(SELECT_TODOS, sqlPro);
			statement = coneccion.prepareStatement(sqlPro.toString());
			filaUsuario = statement.executeQuery();
			usuarios = new ArrayList<Usuario>();
			while (filaUsuario.next()) {
				usuarios.add(this.levantarUsuario(filaUsuario));
			}
			return usuarios;
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	@Override
	public int countAll() {
		try {
			this.prepararConsulta("SELECT count(1) as TOTAL FROM usuarios", sqlPro);
			statement = coneccion.prepareStatement(sqlPro.toString());
			filaUsuario = statement.executeQuery();
			filaUsuario.next();
			return filaUsuario.getInt("TOTAL");
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	@Override
	public int insert(Usuario usuarioAInsertar) {
		try {
			this.prepararConsulta("INSERT INTO usuarios (nombreUsuario, tiempo,", sqlPro);
			sqlPro.append(" presupuesto, nombreTipo) VALUES (?, ?, ?, ?)");
			statement = coneccion.prepareStatement(sqlPro.toString());
			statement.setString(1, usuarioAInsertar.getNombre());
			statement.setDouble(2, usuarioAInsertar.getTiempo());
			statement.setDouble(3, usuarioAInsertar.getPresupuesto());
			statement.setString(4, usuarioAInsertar.getPreferencia().name());
			statement.executeUpdate();
			return this.insertarItinerario(usuarioAInsertar);
		} catch (Exception e) {
			throw new InsertDataBaseExcepcion(MENSAJE, e);
		}
	}

	@Override
	public int update(Usuario usuarioAActualizar) {
		try {
			this.prepararConsulta("UPDATE usuarios SET nombreUsuario = ?, tiempo = ?,", sqlPro);
			sqlPro.append(" presupuesto = ? WHERE idUsuario = ?");
			statement = coneccion.prepareStatement(sqlPro.toString());
			statement.setString(1, usuarioAActualizar.getNombre());
			statement.setDouble(2, usuarioAActualizar.getTiempo());
			statement.setDouble(3, usuarioAActualizar.getPresupuesto());
			statement.setInt(3, usuarioAActualizar.getId());
			statement.executeUpdate();
			return this.actualizarItinerario(usuarioAActualizar);
		} catch (Exception e) {
			throw new UpdateDataBaseExcepcion(MENSAJE, e);
		}
	}

	@Override
	public int delete(Usuario usuarioAEliminar) {
		try {
			this.prepararConsulta("DELETE FROM usuarios WHERE idUsuario = ?", sqlPro);
			statement = coneccion.prepareStatement(sqlPro.toString());
			statement.setInt(1, usuarioAEliminar.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			throw new DeleteDataBaseExcepcion(MENSAJE, e);
		}
	}

	@Override
	public Usuario findById(int id) {
		try {
			this.prepararConsulta(SELECT_TODOS, sqlPro);
			sqlPro.append("WHERE idUsuario = ?");
			statement = coneccion.prepareStatement(sqlPro.toString());
			statement.setInt(1, id);
			filaUsuario = statement.executeQuery();
			if (filaUsuario.next()) {
				usuario = this.levantarUsuario(filaUsuario);
			}
			return usuario;
		} catch (Exception e) {
			throw new SelectDataBaseExcepcion(MENSAJE, e);
		}
	}

	private StringBuilder prepararConsulta(String inicio, StringBuilder consulta) throws SQLException {
		coneccion = Controlador.getConnection();
		consulta.setLength(0);
		return consulta.append(inicio);
	}

	private int insertarItinerario(Usuario usuario) throws SQLException {
		int cantidad = 0;
		if (!usuario.getItinerario().isEmpty()) {
			for (Base indice : usuario.getItinerario()) {
				this.prepararConsulta("INSERT INTO itinerario", sqlPro);
				if (indice.getId().startsWith("1.")) {
					sqlPro.append("Promociones (idUsuario, idPromocion) VALUES (?, ?)");
				} else {
					sqlPro.append("Atracciones (idUsuario, idAtraccion) VALUES (?, ?)");
				}
				statement = coneccion.prepareStatement(sqlPro.toString());
				statement.setInt(1, usuario.getId());
				statement.setInt(2, Integer.parseInt(indice.getId().substring(2)));
				cantidad = +statement.executeUpdate();
			}
		}
		return cantidad;
	}

	private int actualizarItinerario(Usuario usuario) throws SQLException {
		if (!usuario.getItinerario().isEmpty()) {
			for (int indice = usuario.getProductos() - 1; indice <= usuario.getItinerario().size(); indice++) {
				this.prepararConsulta("INSERT INTO itinerario", sqlPro);
				if (usuario.getItinerario().get(indice).getId().startsWith("1.")) {
					sqlPro.append("Promociones (idUsuario, idPromocion) VALUES (?, ?)");
				} else {
					sqlPro.append("Atracciones (idUsuario, idAtraccion) VALUES (?, ?)");
				}
				statement = coneccion.prepareStatement(sqlPro.toString());
				statement.setInt(1, usuario.getId());
				statement.setInt(2, Integer.parseInt(usuario.getItinerario().get(indice).getId().substring(2)));
				statement.executeUpdate();
			}
			usuario.setProductos(usuario.getItinerario().size());
		}
		return this.insertarItinerario(usuario);
	}

	private ArrayList<String> seleccionarItinerario(Usuario usuario) throws SQLException {
		ids = new ArrayList<String>();
		int productos = 0;
		this.prepararConsulta("SELECT idItinerario FROM itinerarioPromociones", sqlPro);
		sqlPro.append(" NATURAL JOIN usuarios WHERE usuarios.idUsuario = ?");
		statement = coneccion.prepareStatement(sqlPro.toString());
		statement.setInt(1, usuario.getId());
		filaUsuario = statement.executeQuery();
		while (filaUsuario.next()) {
			ids.add("1." + filaUsuario.getInt("pro"));
			productos++;
		}
		this.prepararConsulta("SELECT idItinerario FROM itinerarioAtracciones", sqlPro);
		sqlPro.append(" NATURAL JOIN usuarios WHERE usuarios.idUsuario = ?");
		statement = coneccion.prepareStatement(sqlPro.toString());
		statement.setInt(1, usuario.getId());
		filaUsuario = statement.executeQuery();
		while (filaUsuario.next()) {
			ids.add("2." + filaUsuario.getInt("atr"));
			productos++;
		}
		usuario.setProductos(productos);
		return ids;
	}

	private Usuario levantarUsuario(ResultSet filaUsuario) throws SQLException {
		tipo = TipoAtraccion.valueOf(filaUsuario.getString(5).toUpperCase());
		usuario = new Usuario(filaUsuario.getInt(1), filaUsuario.getString(2), filaUsuario.getDouble(3),
				filaUsuario.getDouble(4), tipo);
		this.seleccionarItinerario(usuario);
		for (String id : ids) {
			if (id.startsWith("1.")) {
				usuario.addItinerario(DAOFactory.getPromocionDAO().findById(Integer.parseInt(id.substring(2))));
			} else {
				usuario.addItinerario(DAOFactory.getAtraccionDAO().findById(Integer.parseInt(id.substring(2))));
			}
		}
		return usuario;
	}
}
