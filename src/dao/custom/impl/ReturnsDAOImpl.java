package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ReturnsDAO;
import entity.Returns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnsDAOImpl implements ReturnsDAO {
    @Override
    public boolean add(Returns returns) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO returnItems VALUES(?,?,?,?,?,?,?,?)", returns.getReturnId(), returns.getOrderId(), returns.getCustomerId(), returns.getItemCode(), returns.getItemDescription(), returns.getReturnQty(), returns.getReturnReason(), returns.getItemPrice());
    }

    @Override
    public boolean update(Returns returns) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Returns> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM returnItems");
        ArrayList<Returns> returns = new ArrayList<>();
        while (rst.next()) {
            returns.add(new Returns(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getInt(6), rst.getString(7), rst.getDouble(8)));
        }
        return returns;
    }

    @Override
    public Returns search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean ifReturnExists(String returnId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM returnItems where returnId=?", returnId).next();
    }

    @Override
    public ArrayList<String> getOnlyReturnIds() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT returnId FROM returnItems");
        ArrayList<String> getAll = new ArrayList<>();
        while (rst.next()) {
            getAll.add(rst.getString(1));
        }
        return getAll;
    }

    @Override
    public String generateReturnId() throws SQLException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT returnId FROM ReturnItems order by returnId desc limit 1");
        if (resultSet.next()) {
            int temp = Integer.parseInt(resultSet.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "RE-00" + temp;
            } else if (temp >= 99) {
                return "RE-0" + temp;
            } else {
                return "RE-" + temp;
            }
        } else {
            return "RE-001";
        }
    }

    @Override
    public double getSumOfReturnItems() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select sum(itemPrice) from returnItems;");
        while (rst.next()){
            return rst.getDouble(1);
        }return 0.0;
    }
}
