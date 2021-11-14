package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.NormalOrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static util.CommonFunctions.setDate;

public class NormalOrderDAOImpl implements NormalOrderDAO {
    @Override
    public boolean add(Order order) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO NormalOrder VALUES(?,?,?,?,?)",order.getOrderId(),order.getCustomerId(),order.getOrderDate(),order.getOrderTime(),order.getOrderCost());
    }

    @Override
    public boolean update(Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM NormalOrder");
        ArrayList<Order>orders=new ArrayList<>();
        while (rst.next()){
            orders.add(new Order(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5)));
        }return orders;
    }

    @Override
    public Order search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM NormalOrder where orderId=?", s);
        if (rst.next()){
            return new Order(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5));
        }return null;
    }

    @Override
    public boolean ifNormalOrderExists(String orderId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM NormalOrder where orderId=?",orderId).next();
    }

    @Override
    public String getOrderId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId from NormalOrder order by orderId desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "O-00"+temp;
            }else if (temp<=99){
                return "O-0"+temp;
            }else {
                return "O-"+temp;
            }
        }else {
            return "O-001";
        }
    }

    @Override
    public int getAllOrdersCount() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(orderId) FROM NormalOrder");
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }

    @Override
    public int getTotalOrdersPerDay() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select count(orderId) from normalOrder where orderdate=?", setDate);
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }

    @Override
    public double getDailyNormalOrderIncome() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select orderprice from normalorder where orderdate=?", setDate);
        while (rst.next()){
            return rst.getDouble(1);
        }return 0.0;
    }

    @Override
    public double getSumOfTotalNormalOrdersIncome() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(orderPrice) as orderPrice FROM NormalOrder order by orderdate desc limit 1");
        while (rst.next()){
            return rst.getDouble(1);
        }return 0.0;
    }
}
