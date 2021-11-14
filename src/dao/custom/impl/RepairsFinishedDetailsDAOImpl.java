package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairsFinishedDetailsDAO;
import entity.RepairsFinishedDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsFinishedDetailsDAOImpl implements RepairsFinishedDetailsDAO {
    @Override
    public boolean add(RepairsFinishedDetails repairsFinishedDetails) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO RepairFinishedDetails VALUES(?,?,?,?,?,?)",repairsFinishedDetails.getRepairId(),repairsFinishedDetails.getRepairCount(),repairsFinishedDetails.getRepairItemDescription(),repairsFinishedDetails.getRepairType(),repairsFinishedDetails.getRepairPart(),repairsFinishedDetails.getRepairCost());
    }

    @Override
    public boolean update(RepairsFinishedDetails repairsFinishedDetails) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<RepairsFinishedDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public RepairsFinishedDetails search(String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<RepairsFinishedDetails> getAllFromRepairId(String repairId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM RepairFinishedDetails WHERE repairId=?",repairId);
        ArrayList<RepairsFinishedDetails>all=new ArrayList<>();
        while (rst.next()){
            all.add(new RepairsFinishedDetails(rst.getString(1),rst.getInt(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6)));
        }return all;
    }
}
