package comparadores;

import java.util.Comparator;

import clases.Base;
import clases.TipoAtraccion;

public class PrimeroPaisaje implements Comparator<Base> {

	@Override
	public int compare(Base primero, Base segundo) {
		if (!primero.getTipoAtraccion().equals(segundo.getTipoAtraccion()))
			if (primero.getTipoAtraccion().equals(TipoAtraccion.PAISAJE))
				return -1;
			else if (segundo.getTipoAtraccion().equals(TipoAtraccion.PAISAJE))
				return 1;
			else
				return 0;
		else
			return 0;
	}

}
