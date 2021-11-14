package dao.custom;

import dao.CrudDAO;
import entity.RepairID;

import java.sql.SQLException;

public interface RepairIdDAO extends CrudDAO<RepairID,String> {
    public String generateRepairId() throws SQLException;
}
