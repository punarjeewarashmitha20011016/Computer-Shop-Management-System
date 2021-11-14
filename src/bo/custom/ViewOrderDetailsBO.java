package bo.custom;

import bo.SuperBO;
import dto.NormalOrderDTO;
import dto.RepairOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewOrderDetailsBO extends SuperBO {
    public NormalOrderDTO searchNormalOrders(String orderId) throws SQLException;
    public int getNormalOrderDetailsItemCount(String id) throws SQLException;
    public RepairOrderDTO searchRepairOrders(String repairId) throws SQLException;
    public int getRepairOrderRepairsCount(String orderId) throws SQLException;
    public ArrayList<String> getNormalOrderIds() throws SQLException;
    public ArrayList<String>getRepairOrderIds() throws SQLException;
}
