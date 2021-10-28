package comparadores;

import java.util.Comparator;

import clases.Atraccion;

public class NombreDeAtraccionComparador implements Comparator<Atraccion> {

	public int compare(Atraccion atraccionFija, Atraccion atraccionIndice) {
		return atraccionFija.getId().compareTo(atraccionIndice.getId());
	}

}
