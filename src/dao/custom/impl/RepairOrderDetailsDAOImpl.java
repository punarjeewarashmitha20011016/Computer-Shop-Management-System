package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairOrderDetailsDAO;
import entity.RepairOrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairOrderDetailsDAOImpl implements RepairOrderDetailsDAO {
    @Override
    public boolean add(RepairOrderDetails repairOrderDetails) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO RepairOrderDetails VALUES(?,?,?,?,?,?,?,?)",repairOrderDetails.getOrderId(),repairOrderDetails.getRepairCount(),repairOrderDetails.getRepairItemDescription(),repairOrderDetails.getRepairType(),repairOrderDetails.getRepairPart(),repairOrderDetails.getRepairStartDate(),repairOrderDetails.getRepairFinishedDate(),repairOrderDetails.getRepairItemPrice());
    }

    @Override
    public boolean update(RepairOrderDetails repairOrderDetails) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<RepairOrderDetails> getAll() throws SQLException {
       return null;
    }

    @Override
    public RepairOrderDetails search(String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<RepairOrderDetails> getRepairOrderDetailsByOrderId(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairOrderDetails where orderId=?", id);
        ArrayList<RepairOrderDetails>details=new ArrayList<>();
        while (rst.next()){
            details.add(new RepairOrderDetails(rst.getString(1),rst.getInt(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getDouble(8)));
        }return details;
    }

    @Override
    public int getRepairOrdersRepairCounts(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT COUNT(repairCount) FROM RepairOrderDetails WHERE orderId=?", id);
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }
}
