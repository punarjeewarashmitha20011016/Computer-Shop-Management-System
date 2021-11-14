package dao.custom;

import dao.CrudDAO;
import entity.RepairsFinishedDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairsFinishedDetailsDAO extends CrudDAO<RepairsFinishedDetails,String> {
    public ArrayList<RepairsFinishedDetails>getAllFromRepairId(String repairId) throws SQLException;
}
