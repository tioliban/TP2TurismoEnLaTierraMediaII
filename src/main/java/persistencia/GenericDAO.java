package persistencia;

import java.util.List;

public interface GenericDAO<T> {

	public List<T> findAll();

	public int countAll();

	public int insert(T objeto);

	public int update(T objeto);

	public int delete(T objeto);
	
	public T findById(int id);
}
