package persistencia;

import java.util.HashMap;

public interface GenericDAO<T> {

	public HashMap<String,T> findAll();

	public int countAll();

	public int insert(T t);

	public int update(T t);

	public int delete(T t);
}
