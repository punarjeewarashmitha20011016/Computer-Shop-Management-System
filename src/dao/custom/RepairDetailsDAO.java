package dao.custom;

import dao.CrudDAO;
import dto.RepairDetailsDTO;
import entity.RepairDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairDetailsDAO extends CrudDAO<RepairDetails,String> {
    public ArrayList<RepairDetails>searchRepairDetailsAsAList(String repairId) throws SQLException;
}
