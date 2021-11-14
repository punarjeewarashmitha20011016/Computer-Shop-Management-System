package dao.custom;

import dao.CrudDAO;
import entity.RepairsFinished;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsFinishedDAO extends CrudDAO<RepairsFinished,String> {
    public boolean ifRepairsFinishedExists(String repairId) throws SQLException;
    public ArrayList<RepairsFinished>getAllRepairsFinishedById(String repairId) throws SQLException;
    public int getTotalRepairsFinishedCount() throws SQLException;
}
