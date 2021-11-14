package bo.custom.impl;

import bo.custom.ViewIncomeBO;
import dao.DAOFactory;
import dao.custom.*;
import entity.Income;

import java.sql.SQLException;

import static util.CommonFunctions.setDate;

public class ViewIncomeBOImpl implements ViewIncomeBO {
    private NormalOrderDAO normalOrderDAO= (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private RepairOrderDAO repairOrderDAO= (RepairOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDER);
    private ReturnsDAO returnsDAO= (ReturnsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.RETURNS);
    private IncomeDAO incomeDAO= (IncomeDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.INCOME);
    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    @Override
    public double getTotalNormalOrdersIncome() throws SQLException {
        return normalOrderDAO.getSumOfTotalNormalOrdersIncome();
    }

    @Override
    public double getTotalRepairSalesIncome() throws SQLException {
        return repairOrderDAO.getTotalRepairOrdersIncome();
    }
    @Override
    public double getSumOfReturnItems() throws SQLException {
        return returnsDAO.getSumOfReturnItems();
    }

    @Override
    public double getTotalIncome() throws SQLException {
        Income incomeAsAtToday = incomeDAO.getIncomeAsAtToday(setDate);
        return incomeAsAtToday.getTotalIncome();
    }

    @Override
    public double getSumOfTotalItemBuyingPrices() throws SQLException {
        return itemDAO.getSumOfTotalItemBuyingPrice();
    }
}
