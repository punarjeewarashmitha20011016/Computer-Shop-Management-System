package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageCustomerBO extends SuperBO{
    public boolean saveCustomer(CustomerDTO c) throws SQLException;

    public boolean updateCustomer(CustomerDTO c) throws SQLException;

    public boolean deleteCustomer(String id) throws SQLException;

    public String getCustomerId() throws SQLException;

    public ArrayList<CustomerDTO> getAll() throws SQLException;

    public boolean ifCustomerExists(String id) throws SQLException;

    public String getPreviousRegisteredCustomer() throws SQLException;

    public int getCountOfCustomersRegistered() throws SQLException;
}
