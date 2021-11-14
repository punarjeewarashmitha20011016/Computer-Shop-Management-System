package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    public String getCustomerId() throws SQLException;
    public boolean ifCustomerExists(String id) throws SQLException;
    public String getPreviousRegisteredCustomer() throws SQLException;
    public int getCountOfCustomersRegistered() throws SQLException;
    public int totalCustomersInteractToday() throws SQLException;
}
