package DAO;

import java.util.List;

public interface GenericDAO<T> {
	public void add(T entity);
	public void delete(int id);
	public void update(T entity);
	public List<T> getAll();
    public T findById(int id);
}
