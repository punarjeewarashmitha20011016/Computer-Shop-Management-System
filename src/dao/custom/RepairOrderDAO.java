package dao.custom;

import dao.CrudDAO;
import entity.RepairOrder;

import java.sql.SQLException;

public interface RepairOrderDAO extends CrudDAO<RepairOrder,String> {
    public boolean ifRepairOrderExists(String orderId) throws SQLException;
    public String generateRepairOrderId() throws SQLException;
    public int getAllRepairOrdersCount() throws SQLException;
    public int getTotalRepairOrdersPerDay() throws SQLException;
    public double getDailyRepairOrderIncome() throws SQLException;
    public String getLastRepairPlacedDateAndTime() throws SQLException;
    public double getTotalRepairOrdersIncome() throws SQLException;
}
