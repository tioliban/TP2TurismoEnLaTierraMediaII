package comparadores;

import java.util.Comparator;

import clases.Atraccion;

public class TipoDeAtraccionComparador implements Comparator<Atraccion> {

	public int compare(Atraccion atraccionFija, Atraccion atraccionIndice) {
		return atraccionFija.getTipoAtraccion().name().compareTo(atraccionIndice.getTipoAtraccion().name());
	}

}
