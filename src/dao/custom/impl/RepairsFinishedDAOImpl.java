package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairsFinishedDAO;
import entity.RepairsFinished;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsFinishedDAOImpl implements RepairsFinishedDAO {
    @Override
    public boolean add(RepairsFinished repairsFinished) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO repairsFinished VALUES(?,?,?,?,?,?)", repairsFinished.getCustomerId(), repairsFinished.getRepairId(), repairsFinished.getRepairItemDescription(), repairsFinished.getRepairStartDate(), repairsFinished.getRepairFinishedDate(), repairsFinished.getRepairCost());
    }

    @Override
    public boolean update(RepairsFinished repairsFinished) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM repairsFinished WHERE repairId=?", s);
    }

    @Override
    public ArrayList<RepairsFinished> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairsFinished");
        ArrayList<RepairsFinished>repairsFinisheds=new ArrayList<>();
        while (rst.next()){
            repairsFinisheds.add(new RepairsFinished(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6)));
        }return repairsFinisheds;
    }

    @Override
    public RepairsFinished search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean ifRepairsFinishedExists(String repairId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM repairsFinished WHERE repairId=?",repairId).next();
    }

    @Override
    public ArrayList<RepairsFinished> getAllRepairsFinishedById(String repairId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairsFinished WHERE repairId=?", repairId);
        ArrayList<RepairsFinished>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(new RepairsFinished(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6)));
        }return arrayList;
    }

    @Override
    public int getTotalRepairsFinishedCount() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("select count(repairId) from repairsFinished");
        while (rst.next()){
            return rst.getInt(1);
        }return -1;
    }
}
