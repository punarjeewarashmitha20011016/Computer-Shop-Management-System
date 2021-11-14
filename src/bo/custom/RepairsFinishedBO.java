package bo.custom;

import bo.SuperBO;
import dto.RepairsFinishedDTO;
import dto.RepairsFinishedDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsFinishedBO extends SuperBO {
    public ArrayList<String> getRepairIdsFromRepairsFinished() throws SQLException;

    public ArrayList<RepairsFinishedDTO> getAllRepairsFinished() throws SQLException;

    public ArrayList<RepairsFinishedDetailsDTO> getAllFinishedDetailsFromRepairId(String repairId) throws SQLException;
}
