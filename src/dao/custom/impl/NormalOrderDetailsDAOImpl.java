package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.NormalOrderDetailsDAO;
import entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NormalOrderDetailsDAOImpl implements NormalOrderDetailsDAO {
    @Override
    public boolean add(OrderDetails od) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO NormalOrderDetails VALUES(?,?,?,?,?,?,?,?,?,?)", od.getOrderId(), od.getItemCode(), od.getItemDescription(), od.getItemBrand(), od.getItemCategory(), od.getItemRam(), od.getItemStorage(), od.getQtyOnHand(), od.getItemDiscount(), od.getItemCost());
    }

    @Override
    public boolean update(OrderDetails orderDetails) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM NormalOrderDetails");
        ArrayList<OrderDetails>orderDetails=new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6), rst.getDouble(7), rst.getInt(8), rst.getDouble(9), rst.getDouble(10)));
        }return orderDetails;
    }

    @Override
    public OrderDetails search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM NormalOrderDetails where orderId=?", s);
        if (rst.next()) {
            return new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6), rst.getDouble(7), rst.getInt(8), rst.getDouble(9), rst.getDouble(10));
        }
        return null;
    }

    @Override
    public ArrayList<OrderDetails> getAllOrderDetailsSearchedById(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM NormalOrderDetails WHERE orderId=?", id);
        ArrayList<OrderDetails> details = new ArrayList<>();
        while (rst.next()) {
            details.add(new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6), rst.getDouble(7), rst.getInt(8), rst.getDouble(9), rst.getDouble(10)));
        }return details;
    }

    @Override
    public int getNormalOrderDetailsItemCount(String orderId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(itemCode) FROM NormalOrderDetails WHERE orderId=?", orderId);
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }
}
