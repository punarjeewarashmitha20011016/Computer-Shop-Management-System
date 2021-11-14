package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairServicesPartsDAO;
import entity.RepairServicesParts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairServicesPartsDAOImpl implements RepairServicesPartsDAO {
    @Override
    public boolean add(RepairServicesParts repairServicesParts) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO repairServicesParts VALUES(?,?,?,?)", repairServicesParts.getItemCode(), repairServicesParts.getItemDescription(), repairServicesParts.getItemQty(), repairServicesParts.getItemUnitPrice());
    }

    @Override
    public boolean update(RepairServicesParts repairServicesParts) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE repairServicesParts SET itemDescription=?,itemQty=?,itemUnitPrice=? WHERE itemCode=?",repairServicesParts.getItemDescription(),repairServicesParts.getItemQty(),repairServicesParts.getItemUnitPrice(),repairServicesParts.getItemCode());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM repairServicesParts WHERE itemCode=?", s);
    }

    @Override
    public ArrayList<RepairServicesParts> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairServicesParts");
        ArrayList<RepairServicesParts> getAll = new ArrayList<>();
        while (rst.next()) {
            getAll.add(new RepairServicesParts(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4)));
        }
        return getAll;
    }

    @Override
    public RepairServicesParts search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairServicesParts WHERE itemDescription=?", s);
        if (rst.next()){
            return new RepairServicesParts(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4));
        }return null;
    }

    @Override
    public boolean ifRepairServicePartExists(String itemCode) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM repairServicesParts WHERE itemCode=?", itemCode).next();
    }

    @Override
    public RepairServicesParts searchRepairServicesPartsByRepairPart(String repairPart) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairServicesParts WHERE itemDescription=?", repairPart);
        while (rst.next()) {
            return new RepairServicesParts(rst.getString(1), rst.getString(2),rst.getInt(3),rst.getDouble(4));
        }return null;
    }

    @Override
    public boolean updateRepairServicesParts(String repairPart) throws SQLException {
       return CrudUtil.executeUpdate("UPDATE repairServicesParts SET itemQty=(itemQty-1) WHERE itemDescription=?",repairPart);
    }
}
