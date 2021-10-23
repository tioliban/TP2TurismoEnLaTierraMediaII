package comparadores;

import java.util.Comparator;

import clases.Promocion;

public class TipoDeAtraccionDeLaPromocionComparador implements Comparator<Promocion> {

	@Override
	public int compare(Promocion promocionFija, Promocion promocionIndice) {
		return promocionFija.getTipoAtraccion().name().compareTo(promocionIndice.getTipoAtraccion().name());
	}

}
