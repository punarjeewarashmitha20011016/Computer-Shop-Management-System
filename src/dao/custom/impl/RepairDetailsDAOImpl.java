package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairDetailsDAO;
import dto.RepairDetailsDTO;
import entity.RepairDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairDetailsDAOImpl implements RepairDetailsDAO {
    @Override
    public boolean add(RepairDetails repairDetails) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO repairDetails VALUES(?,?,?,?,?,?)",
                repairDetails.getRepairId(),repairDetails.getRepairCount(),repairDetails.getRepairItemDescription(),repairDetails.getRepairType(),repairDetails.getRepairPart(),repairDetails.getRepairCost());
    }

    @Override
    public boolean update(RepairDetails repairDetailsDTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<RepairDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public RepairDetails search(String s) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<RepairDetails> searchRepairDetailsAsAList(String repairId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairDetails WHERE repairId=?", repairId);
        ArrayList<RepairDetails>repairDetails=new ArrayList<>();
        while (rst.next()){
            repairDetails.add(new RepairDetails(rst.getString(1),rst.getInt(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6)));
        }return repairDetails;
    }
}
