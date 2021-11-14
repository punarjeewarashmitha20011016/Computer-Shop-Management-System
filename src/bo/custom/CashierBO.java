package bo.custom;

import bo.SuperBO;
import dto.CashierDTO;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CashierBO extends SuperBO {
    public int getTotalNormalOrdersCount() throws SQLException;

    public int countOfNormalOrderDailySales() throws SQLException;

    public int getTotalCustomersInteractToday() throws SQLException;

    public int getTotalCustomersRegistered() throws SQLException;

    public int getTotalRepairOrdersCount() throws SQLException;

    public int getDailyRepairOrdersCount() throws SQLException;

    public int totalRepairsInProgressCount() throws SQLException;

    public int totalRepairsFinishedCount() throws SQLException;

    public String getLastRepairPlacedDateAndTime() throws SQLException;

    public String getCashierAvatar() throws SQLException;

    public ArrayList<RepairsInProgressDTO> getAllRepairsInProgress() throws SQLException;

    public ArrayList<RepairsFinishedDTO>getAllRepairsFinished() throws SQLException;

    public double getTotalNormalOrdersIncome() throws SQLException;

    public double getTotalRepairSalesIncome() throws SQLException;
}
