package bo.custom;

import bo.SuperBO;
import dto.RepairDetailsDTO;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsInProgressBO extends SuperBO {
    public ArrayList<String> getRepairIdsFromRepairsInProgress() throws SQLException;

    public ArrayList<RepairsInProgressDTO> getAllFromRepairsInProgress() throws SQLException;

    public RepairsInProgressDTO searchRepairsInProgress(String repairId) throws SQLException;

    public boolean deleteRepairsInProgress(String repairId) throws SQLException;

    public ArrayList<RepairDetailsDTO> getRepairDetails(String repairId) throws SQLException;

    public boolean saveRepairsFinished(RepairsFinishedDTO dto) throws SQLException;
}
