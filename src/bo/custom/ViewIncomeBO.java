package bo.custom;

import bo.SuperBO;

import java.sql.SQLException;

public interface ViewIncomeBO extends SuperBO {
    public double getTotalNormalOrdersIncome() throws SQLException;

    public double getTotalRepairSalesIncome() throws SQLException;

    public double getSumOfReturnItems() throws SQLException;

    public double getTotalIncome() throws SQLException;

    public double getSumOfTotalItemBuyingPrices() throws SQLException;
}
