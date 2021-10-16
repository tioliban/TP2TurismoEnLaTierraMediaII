package aplicacion;

import persistencia.ArchivoAtraccion;
import persistencia.ArchivoPromocion;
import persistencia.ArchivoUsuario;

public class App {
	public static void main(String[] args) {
		String archivoUsuario = "usuarios.csv", archivoAtracciones = "atracciones.csv",
				archivoPromociones = "promociones.csv";
		ArchivoUsuario losUsuarios = new ArchivoUsuario(archivoUsuario);
		ArchivoAtraccion lasAtracciones = new ArchivoAtraccion(archivoAtracciones);
		ArchivoPromocion lasPromociones = new ArchivoPromocion(archivoPromociones, lasAtracciones.getAtracciones());
		SugerirProducto laOfertaDeProductos = new SugerirProducto(losUsuarios.getUsuarios(),
				lasPromociones.getPromociones(), lasAtracciones.getAtracciones());
		laOfertaDeProductos.mostrarPorPantalla();
		losUsuarios.generarArchivoUsuario();
	}

}
