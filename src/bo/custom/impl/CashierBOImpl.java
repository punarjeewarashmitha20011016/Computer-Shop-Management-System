package bo.custom.impl;

import bo.custom.CashierBO;
import dao.DAOFactory;
import dao.custom.*;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;
import entity.RepairsFinished;
import entity.RepairsInProgress;

import java.sql.SQLException;
import java.util.ArrayList;

public class CashierBOImpl implements CashierBO {
    private CashierDAO cashierDAO = (CashierDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CASHIER);
    private NormalOrderDAO normalOrderDAO = (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);
    private RepairOrderDAO repairOrderDAO = (RepairOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDER);
    private RepairsInProgressDAO repairsInProgressDAO = (RepairsInProgressDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSINPROGRESS);
    private RepairsFinishedDAO repairsFinishedDAO = (RepairsFinishedDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHED);

    @Override
    public int getTotalNormalOrdersCount() throws SQLException {
        return normalOrderDAO.getAllOrdersCount();
    }

    @Override
    public int countOfNormalOrderDailySales() throws SQLException {
        return normalOrderDAO.getTotalOrdersPerDay();
    }

    @Override
    public int getTotalCustomersInteractToday() throws SQLException {
        return customerDAO.totalCustomersInteractToday();
    }

    @Override
    public int getTotalCustomersRegistered() throws SQLException {
        return customerDAO.getCountOfCustomersRegistered();
    }

    @Override
    public int getTotalRepairOrdersCount() throws SQLException {
        return repairOrderDAO.getAllRepairOrdersCount();
    }

    @Override
    public int getDailyRepairOrdersCount() throws SQLException {
        return repairOrderDAO.getTotalRepairOrdersPerDay();
    }

    @Override
    public int totalRepairsInProgressCount() throws SQLException {
        return repairsInProgressDAO.totalRepairsInProgressCount();
    }

    @Override
    public int totalRepairsFinishedCount() throws SQLException {
        return repairsFinishedDAO.getTotalRepairsFinishedCount();
    }

    @Override
    public String getLastRepairPlacedDateAndTime() throws SQLException {
        return repairOrderDAO.getLastRepairPlacedDateAndTime();
    }

    @Override
    public String getCashierAvatar() throws SQLException {
        return cashierDAO.getCashierAvatar();
    }

    @Override
    public ArrayList<RepairsInProgressDTO> getAllRepairsInProgress() throws SQLException {
        ArrayList<RepairsInProgress> all = repairsInProgressDAO.getAll();
        ArrayList<RepairsInProgressDTO> dtos = new ArrayList<>();
        for (RepairsInProgress dto : all
        ) {
            dtos.add(new RepairsInProgressDTO(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairEstimatedFinishingDate(), dto.getRepairCost()));
        }
        return dtos;
    }

    @Override
    public ArrayList<RepairsFinishedDTO> getAllRepairsFinished() throws SQLException {
        ArrayList<RepairsFinished> all = repairsFinishedDAO.getAll();
        ArrayList<RepairsFinishedDTO> dtos = new ArrayList<>();
        for (RepairsFinished dto : all
        ) {
            dtos.add(new RepairsFinishedDTO(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairFinishedDate(), dto.getRepairCost()));
        }
        return dtos;
    }

    @Override
    public double getTotalNormalOrdersIncome() throws SQLException {
        return normalOrderDAO.getSumOfTotalNormalOrdersIncome();
    }

    @Override
    public double getTotalRepairSalesIncome() throws SQLException {
        return repairOrderDAO.getTotalRepairOrdersIncome();
    }
}
