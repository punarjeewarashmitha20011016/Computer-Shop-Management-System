package bo.custom;

import bo.SuperBO;
import dto.RepairsFinishedDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsFinishedDetailsBO extends SuperBO {
    public ArrayList<RepairsFinishedDetailsDTO> getAllFinishedDetailsFromRepairId(String repairId) throws SQLException;
}
