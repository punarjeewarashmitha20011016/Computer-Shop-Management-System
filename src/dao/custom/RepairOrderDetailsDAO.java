package dao.custom;

import dao.CrudDAO;
import entity.RepairOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairOrderDetailsDAO extends CrudDAO<RepairOrderDetails,String> {
    public ArrayList<RepairOrderDetails>getRepairOrderDetailsByOrderId(String id) throws SQLException;
    public int getRepairOrdersRepairCounts(String id) throws SQLException;
}
