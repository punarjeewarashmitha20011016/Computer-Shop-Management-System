package model;

import java.sql.SQLException;

public interface CrudMain{

    boolean save(Object obj) throws SQLException;
    boolean update(Object obj) throws SQLException;
    boolean delete(String s) throws SQLException;
}
