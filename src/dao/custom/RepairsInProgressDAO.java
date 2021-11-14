package dao.custom;

import dao.CrudDAO;
import entity.RepairsInProgress;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsInProgressDAO extends CrudDAO<RepairsInProgress, String> {
    public ArrayList<String> getRepairsInProgressIds() throws SQLException;
    public boolean ifRepairsInProgressIdExists(String repairId) throws SQLException;
    public int totalRepairsInProgressCount() throws SQLException;
}
