package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.RepairDetailsDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairItemsBO extends SuperBO {
    public RepairServicesPartsDTO searchRepairServicesParts(String repairPart) throws SQLException;

    public ObservableList<String> getRepairParts() throws SQLException;

    public RepairServicesTypesDTO searchRepairType(String repairType) throws SQLException;

    public String generateRepairId() throws SQLException;

    public ObservableList<String> getRepairTypes() throws SQLException;

    public ObservableList<String> getOnlyCustomerIds() throws SQLException;

    public ArrayList<String> getRepairIdsFromRepairsInProgress() throws SQLException;
    public CustomerDTO searchCustomers(String customerId) throws SQLException;
    public ArrayList<RepairDetailsDTO> getRepairDetails(String repairId) throws SQLException;
}
