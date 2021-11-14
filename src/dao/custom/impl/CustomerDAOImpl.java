package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static util.CommonFunctions.setDate;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO Customer Values(?,?,?,?)", customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerContactNo(), customer.getCustomerAddress());
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE Customer SET customerName=?,customerTelNo=?,customerAddress=? where customerId=?", customer.getCustomerName(), customer.getCustomerContactNo(), customer.getCustomerAddress(), customer.getCustomerId());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM Customer Where customerId=?", s);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
        }
        return list;
    }

    @Override
    public Customer search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE customerId=?", s);
        if (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
        }return null;
    }

    @Override
    public String getCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT customerId FROM CUSTOMER ORDER BY customerId DESC LIMIT 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "C-00" + temp;
            } else if (temp <= 99) {
                return "C-0" + temp;
            } else {
                return "C-" + temp;
            }
        } else {
            return "C-001";
        }
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException {
        return CrudUtil.executeQuery("SELECT customerId from Customer where customerId=?", id).next();
    }

    @Override
    public String getPreviousRegisteredCustomer() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT customerName FROM customer order by customerId desc limit 1");
        if (rst.next()){
            return rst.getString(1);
        }return null;
    }

    @Override
    public int getCountOfCustomersRegistered() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT count(customerId) from Customer");
        if (rst.next()){
            return rst.getInt(1);
        }return -1;
    }

    @Override
    public int totalCustomersInteractToday() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select count(customerId) from normalOrder where orderdate=?", setDate);
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }
}
