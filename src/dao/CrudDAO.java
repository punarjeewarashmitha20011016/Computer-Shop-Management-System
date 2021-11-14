package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO {
    public boolean add(T t) throws SQLException;

    public boolean update(T t) throws SQLException;

    public boolean delete(ID id) throws SQLException;

    public ArrayList<T> getAll() throws SQLException;

    public T search(ID id) throws SQLException;
}
