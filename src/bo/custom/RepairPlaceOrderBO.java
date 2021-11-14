package bo.custom;

import bo.SuperBO;
import dto.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairPlaceOrderBO extends SuperBO {
    //------------------------------------------------------------------------------------
    public CustomerDTO searchCustomers(String customerId) throws SQLException;


    //------------------------------------------------------------------------------------

    public ArrayList<RepairsFinishedDTO> getAllRepairsFinishedById(String repairId) throws SQLException;

    public boolean deleteFromRepairsFinished(String repairId) throws SQLException;

    //------------------------------------------------------------------------------------
    public String generateOrderId() throws SQLException;

    public boolean saveRepairOrder(RepairOrderDTO dto) throws SQLException;

    //------------------------------------------------------------------------------------
    public boolean ifIncomeExists() throws SQLException;

    public boolean saveIncome(IncomeDTO incomeDTO) throws SQLException;

    public boolean updateRepairSalesIncome(IncomeDTO incomeDTO) throws SQLException;
}
