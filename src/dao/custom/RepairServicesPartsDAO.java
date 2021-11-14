package dao.custom;

import dao.CrudDAO;
import entity.RepairServicesParts;

import java.sql.SQLException;

public interface RepairServicesPartsDAO extends CrudDAO<RepairServicesParts, String> {
    public boolean ifRepairServicePartExists(String itemCode) throws SQLException;
    public RepairServicesParts searchRepairServicesPartsByRepairPart(String repairPart) throws SQLException;
    public boolean updateRepairServicesParts(String repairPart) throws SQLException;
}