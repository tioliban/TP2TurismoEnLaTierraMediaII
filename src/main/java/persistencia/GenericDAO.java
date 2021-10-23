package persistencia;

import java.util.ArrayList;

public interface GenericDAO<T> {

	public ArrayList<T> findAll();

	public int countAll();

	public int insert(T objeto);

	public int update(T objeto);

	public int delete(T objeto);
	
	public T findById(int id);
}
