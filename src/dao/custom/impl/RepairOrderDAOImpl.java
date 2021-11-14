package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairOrderDAO;
import entity.RepairOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static util.CommonFunctions.setDate;

public class RepairOrderDAOImpl implements RepairOrderDAO {
    @Override
    public boolean add(RepairOrder repairOrder) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO RepairOrder VALUES(?,?,?,?,?,?,?)", repairOrder.getOrderId(), repairOrder.getRepairId(), repairOrder.getCustomerId(), repairOrder.getOrderDate(), repairOrder.getOrderTime(), repairOrder.getOrderDiscount(), repairOrder.getOrderCost());
    }

    @Override
    public boolean update(RepairOrder repairOrder) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<RepairOrder> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM RepairOrder");
        ArrayList<RepairOrder>getAll=new ArrayList<>();
        while (rst.next()){
            getAll.add(new RepairOrder(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getInt(6),rst.getDouble(7)));
        }return getAll;
    }

    @Override
    public RepairOrder search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM RepairOrder WHERE orderId=?", s);
        while (rst.next()){
            return new RepairOrder(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getInt(6),rst.getDouble(7));
        }return null;
    }

    @Override
    public boolean ifRepairOrderExists(String orderId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM RepairOrder WHERE orderId=?", orderId).next();
    }

    @Override
    public String generateRepairOrderId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId from RepairOrder order by orderId desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "RO-00" + temp;
            } else if (temp <= 99) {
                return "RO-0" + temp;
            } else {
                return "RO-" + temp;
            }
        } else {
            return "RO-001";
        }
    }

    @Override
    public int getAllRepairOrdersCount() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(orderId) FROM RepairOrder");
        while (rst.next()) {
            return rst.getInt(1);
        }
        return -1;
    }

    @Override
    public int getTotalRepairOrdersPerDay() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select count(orderid) from repairorder where orderdate=?", setDate);
        while (rst.next()) {
            return rst.getInt(1);
        }
        return -1;
    }

    @Override
    public double getDailyRepairOrderIncome() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select orderprice from repairorder where orderdate=?", setDate);
        while (rst.next()) {
            return rst.getDouble(1);
        }
        return 0.0;
    }

    @Override
    public String getLastRepairPlacedDateAndTime() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderDate,orderTime from repairOrder order by orderId desc limit 1");
        while (rst.next()){
            return rst.getString(1) + " ~ " + rst.getString(2);
        }return null;
    }

    @Override
    public double getTotalRepairOrdersIncome() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(orderPrice) as orderPrice FROM RepairOrder order by orderdate desc limit 1");
        while (rst.next()){
            return rst.getDouble(1);
        }return 0.0;
    }
}
