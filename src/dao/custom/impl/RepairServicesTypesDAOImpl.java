package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.RepairServicesTypesDAO;
import entity.RepairServicesTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairServicesTypesDAOImpl implements RepairServicesTypesDAO {
    @Override
    public boolean add(RepairServicesTypes repairServicesTypes) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO repairServicesType VALUES(?,?)",repairServicesTypes.getServiceType(),repairServicesTypes.getServiceCost());
    }

    @Override
    public boolean update(RepairServicesTypes repairServicesTypes) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE repairServicesType SET serviceCost=? WHERE serviceType=?",repairServicesTypes.getServiceCost(),repairServicesTypes.getServiceType());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM repairServicesType WHERE serviceType=?",s);
    }

    @Override
    public ArrayList<RepairServicesTypes> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairServicesType");
        ArrayList<RepairServicesTypes>repairServicesTypes=new ArrayList<>();
        while (rst.next()){
            repairServicesTypes.add(new RepairServicesTypes(rst.getString(1),rst.getDouble(2)));
        }return repairServicesTypes;
    }

    @Override
    public RepairServicesTypes search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM repairServicesType WHERE serviceType=?", s);
        if (rst.next()){
            return new RepairServicesTypes(rst.getString(1),rst.getDouble(2));
        }return null;
    }

    @Override
    public boolean ifRepairTypeExists(String repairType) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM repairServicesType WHERE serviceType=?",repairType).next();
    }
}
