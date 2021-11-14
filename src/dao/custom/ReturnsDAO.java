package dao.custom;

import dao.CrudDAO;
import entity.Returns;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnsDAO extends CrudDAO<Returns, String> {
    public boolean ifReturnExists(String returnId) throws SQLException;
    public ArrayList<String>getOnlyReturnIds() throws SQLException;
    public String generateReturnId() throws SQLException;
    public double getSumOfReturnItems() throws SQLException;
}
