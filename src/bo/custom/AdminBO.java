package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminBO extends SuperBO {
    public ArrayList<ItemDTO> getLowestRemainingItemsInTheStock() throws SQLException;

    public int getAllNormalOrdersCount() throws SQLException;

    public int getAllRepairOrdersCount() throws SQLException;

    public int getTotalOrdersPerDay() throws SQLException;

    public int getTotalRepairOrdersPerDay() throws SQLException;

    public String getMostSellingItem() throws SQLException;

    public double getDailyNormalOrderIncome() throws SQLException;

    public double getDailyRepairOrderIncome() throws SQLException;

    public String getAdminAvatar() throws SQLException;

    public double getTotalNormalOrdersIncome() throws SQLException;

    public double getTotalRepairSalesIncome() throws SQLException;
}
