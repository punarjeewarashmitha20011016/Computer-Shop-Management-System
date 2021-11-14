package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairsInProgressDAO;
import entity.RepairsInProgress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsInProgressDAOImpl implements RepairsInProgressDAO {
    @Override
    public boolean add(RepairsInProgress repairsInProgress) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO repairsInProgress VALUES(?,?,?,?,?,?)",
                repairsInProgress.getCustomerId(),repairsInProgress.getRepairId(),
                repairsInProgress.getRepairItemDescription(),repairsInProgress.getRepairStartDate(),
                repairsInProgress.getRepairEstimatedFinishingDate(),repairsInProgress.getRepairCost());
    }

    @Override
    public boolean update(RepairsInProgress repairsInProgress) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM repairsInProgress where repairId=?",s);
    }

    @Override
    public ArrayList<RepairsInProgress> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairsInProgress");
        ArrayList<RepairsInProgress>all=new ArrayList<>();
        while (rst.next()){
            all.add(new RepairsInProgress(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6)));
        }return all;
    }

    @Override
    public RepairsInProgress search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM RepairsInProgress WHERE repairId=?",s);
        if (rst.next()) {
            return new RepairsInProgress(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6));
        }return null;
    }

    @Override
    public ArrayList<String> getRepairsInProgressIds() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT repairId from repairsInProgress");
        ArrayList<String>list=new ArrayList<>();
        while (rst.next()){
            list.add(rst.getString(1));
        }return list;
    }

    @Override
    public boolean ifRepairsInProgressIdExists(String repairId) throws SQLException {
       return CrudUtil.executeQuery("SELECT * FROM RepairsInProgress WHERE repairId=?",repairId).next();
    }

    @Override
    public int totalRepairsInProgressCount() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select count(repairId) from repairsInProgress");
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }
}
