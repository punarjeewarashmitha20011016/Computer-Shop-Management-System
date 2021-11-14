package bo.custom.impl;

import bo.custom.ManageCustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerBOImpl implements ManageCustomerBO {
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO c) throws SQLException {
        return customerDAO.add(new Customer(c.getCustomerId(), c.getCustomerName(), c.getCustomerContactNo(), c.getCustomerAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO c) throws SQLException {
        return customerDAO.update(new Customer(c.getCustomerId(), c.getCustomerName(), c.getCustomerContactNo(), c.getCustomerAddress()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public String getCustomerId() throws SQLException {
        return customerDAO.getCustomerId();
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer c : all
        ) {
            customerDTOS.add(new CustomerDTO(c.getCustomerId(), c.getCustomerName(), c.getCustomerContactNo(), c.getCustomerAddress()));
        }
        return customerDTOS;
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException {
        return customerDAO.ifCustomerExists(id);
    }

    @Override
    public String getPreviousRegisteredCustomer() throws SQLException {
        return customerDAO.getPreviousRegisteredCustomer();
    }

    @Override
    public int getCountOfCustomersRegistered() throws SQLException {
        return customerDAO.getCountOfCustomersRegistered();
    }

}
