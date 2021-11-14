package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairIdDAO;
import db.DbConnection;
import entity.RepairID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairIdDAOImpl implements RepairIdDAO {
    @Override
    public boolean add(RepairID repairID) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO GenerateRepairId VALUES(?)",repairID.getRepairId());
    }

    @Override
    public boolean update(RepairID repairID) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM GenerateRepairId WHERE repairId=?",s);
    }

    @Override
    public ArrayList<RepairID> getAll() throws SQLException {
        return null;
    }

    @Override
    public RepairID search(String s) throws SQLException {
        return null;
    }

    @Override
    public String generateRepairId() throws SQLException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT repairId from GenerateRepairId order by repairId desc limit 1").executeQuery();
        if (resultSet.next()) {
            int temp = Integer.parseInt(resultSet.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "R-00" + temp;
            } else if (temp <= 99) {
                return "R-0" + temp;
            } else {
                return "R-" + temp;
            }
        } else {
            return "R-001";
        }
    }
}
