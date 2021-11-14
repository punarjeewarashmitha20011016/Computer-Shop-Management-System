package bo.custom.impl;

import bo.custom.RepairPlaceOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.*;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairPlaceOrderBOImpl implements RepairPlaceOrderBO {
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);
    private RepairsFinishedDAO repairsFinishedDAO = (RepairsFinishedDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHED);
    private RepairOrderDAO repairOrderDAO = (RepairOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDER);
    private RepairOrderDetailsDAO repairOrderDetailsDAO = (RepairOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDERDETAILS);
    private IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.INCOME);

    @Override
    public CustomerDTO searchCustomers(String customerId) throws SQLException {
        Customer search = customerDAO.search(customerId);
        return new CustomerDTO(search.getCustomerId(), search.getCustomerName(), search.getCustomerContactNo(), search.getCustomerAddress());
    }

    //----------------------------------------------------------------------------------------------------------------------


    @Override
    public ArrayList<RepairsFinishedDTO> getAllRepairsFinishedById(String repairId) throws SQLException {
        ArrayList<RepairsFinished> allRepairsFinishedById = repairsFinishedDAO.getAllRepairsFinishedById(repairId);
        ArrayList<RepairsFinishedDTO> all = new ArrayList<>();
        for (RepairsFinished r : allRepairsFinishedById
        ) {
            all.add(new RepairsFinishedDTO(r.getCustomerId(), r.getRepairId(), r.getRepairItemDescription(), r.getRepairStartDate(), r.getRepairFinishedDate(), r.getRepairCost()));
        }
        return all;
    }


    @Override
    public boolean deleteFromRepairsFinished(String repairId) throws SQLException {
        return repairsFinishedDAO.delete(repairId);
    }

    @Override
    public String generateOrderId() throws SQLException {
        return repairOrderDAO.generateRepairOrderId();
    }

    @Override
    public boolean saveRepairOrder(RepairOrderDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        boolean exists = repairOrderDAO.ifRepairOrderExists(dto.getOrderId());
        if (exists) {
            return false;
        }
        connection.setAutoCommit(false);
        RepairOrder repairOrder = new RepairOrder(dto.getOrderId(), dto.getRepairId(), dto.getCustomerId(), dto.getOrderDate(), dto.getOrderTime(), dto.getOrderDiscount(), dto.getOrderCost());
        if (!repairOrderDAO.add(repairOrder)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (RepairOrderDetailsDTO r : dto.getRepairDetailsArrayList()
        ) {
            RepairOrderDetails od = new RepairOrderDetails(r.getOrderId(), r.getRepairCount(), r.getRepairItemDescription(), r.getRepairType(), r.getRepairPart(), r.getRepairStartDate(), r.getRepairFinishedDate(), r.getRepairItemPrice());
            if (!repairOrderDetailsDAO.add(od)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean ifIncomeExists() throws SQLException {
        return incomeDAO.ifIncomeExists();
    }

    @Override
    public boolean saveIncome(IncomeDTO incomeDTO) throws SQLException {
        return incomeDAO.add(new Income(incomeDTO.getDateAsPerIncome(), incomeDTO.getNormalSalesIncome(), incomeDTO.getRepairSalesIncome(), incomeDTO.getTotalIncome()));
    }

    @Override
    public boolean updateRepairSalesIncome(IncomeDTO incomeDTO) throws SQLException {
        Income incomeAsAtToday = incomeDAO.getIncomeAsAtToday(incomeDTO.getDateAsPerIncome());
        incomeAsAtToday.setRepairSalesIncome(incomeAsAtToday.getRepairSalesIncome() + incomeDTO.getRepairSalesIncome());
        incomeAsAtToday.setTotalIncome(incomeAsAtToday.getTotalIncome() + incomeDTO.getTotalIncome());
        if (incomeDAO.updateRepairSalesIncome(incomeAsAtToday)) {
            return true;
        } else {
            return false;
        }
    }


}
