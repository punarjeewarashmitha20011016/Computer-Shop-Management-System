package bo.custom;

import bo.SuperBO;
import dto.RepairOrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairOrderDetailsBO extends SuperBO {
    public ArrayList<RepairOrderDetailsDTO> getRepairOrderDetailsByOrderId(String id) throws SQLException;
}
