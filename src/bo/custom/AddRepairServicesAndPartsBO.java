package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AddRepairServicesAndPartsBO extends SuperBO {
    public boolean saveRepairServicesParts(RepairServicesPartsDTO dto) throws SQLException;

    public boolean deleteRepairServicesParts(String itemCode) throws SQLException;

    public boolean ifItemExists(String itemCode) throws SQLException;

    public boolean ifRepairServicePartExists(String itemCode) throws SQLException;

    public ArrayList<RepairServicesPartsDTO> getAllRepairServicesParts() throws SQLException;

    public ItemDTO searchItems(String itemCode) throws SQLException;


    //------------------------------------------------------------------------------------

    public boolean saveRepairServiceType(RepairServicesTypesDTO repairServicesTypes) throws SQLException;

    public boolean updateServiceType(RepairServicesTypesDTO dto) throws SQLException;

    public boolean deleteRepairServiceType(String repairType) throws SQLException;

    public ArrayList<RepairServicesTypesDTO> getAllRepairServicesTypes() throws SQLException;

    public boolean ifRepairTypeExists(String repairType) throws SQLException;

}
