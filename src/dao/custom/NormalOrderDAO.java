package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.SQLException;

public interface NormalOrderDAO extends CrudDAO<Order, String> {
    public boolean ifNormalOrderExists(String orderId) throws SQLException;
    public String getOrderId() throws SQLException;
    public int getAllOrdersCount() throws SQLException;
    public int getTotalOrdersPerDay() throws SQLException;
    public double getDailyNormalOrderIncome() throws SQLException;
    public double getSumOfTotalNormalOrdersIncome() throws SQLException;
}
