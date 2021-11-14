package dao.custom;

import dao.CrudDAO;
import entity.RepairServicesTypes;

import java.sql.SQLException;

public interface RepairServicesTypesDAO extends CrudDAO<RepairServicesTypes,String> {
    public boolean ifRepairTypeExists(String repairType) throws SQLException;
}
