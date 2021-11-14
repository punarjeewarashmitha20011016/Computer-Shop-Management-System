package bo.custom;

import bo.SuperBO;
import dto.RepairIdDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairsInProgressDTO;

import java.sql.SQLException;

public interface AddRepairsBO extends SuperBO {
    public boolean saveRepairIdInRepairIdTable(RepairIdDTO dto) throws SQLException;
    public boolean deleteRepairId(String id) throws SQLException;
    public boolean saveRepairsInProgress(RepairsInProgressDTO dto, int count) throws SQLException;
    public String generateRepairId() throws SQLException;
}
