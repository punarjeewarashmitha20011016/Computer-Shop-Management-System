package bo.custom.impl;

import bo.custom.RepairItemsBO;
import dao.DAOFactory;
import dao.custom.*;
import dto.CustomerDTO;
import dto.RepairDetailsDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;
import entity.Customer;
import entity.RepairDetails;
import entity.RepairServicesParts;
import entity.RepairServicesTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairItemsBOImpl implements RepairItemsBO {
    private CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);
    private RepairServicesPartsDAO repairServicesPartsDAO= (RepairServicesPartsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSERVICESPARTS);
    private RepairServicesTypesDAO repairServicesTypesDAO= (RepairServicesTypesDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSERVICESTYPE);
    private RepairsInProgressDAO repairsInProgressDAO= (RepairsInProgressDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSINPROGRESS);
    private RepairIdDAO repairIdDAO= (RepairIdDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.GENERATEREPAIRID);
    private RepairDetailsDAO repairDetailsDAO= (RepairDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRDETAILS);

    @Override
    public RepairServicesPartsDTO searchRepairServicesParts(String repairPart) throws SQLException {
        RepairServicesParts repairServicesParts = repairServicesPartsDAO.searchRepairServicesPartsByRepairPart(repairPart);
        return new RepairServicesPartsDTO(repairServicesParts.getItemCode(),repairServicesParts.getItemDescription(),repairServicesParts.getItemQty(),repairServicesParts.getItemUnitPrice());
    }

    @Override
    public ObservableList<String> getRepairParts() throws SQLException {
        ArrayList<RepairServicesParts> all = repairServicesPartsDAO.getAll();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (RepairServicesParts r : all
        ) {
            list.add(r.getItemDescription());
        }
        return list;
    }
    @Override
    public RepairServicesTypesDTO searchRepairType(String repairType) throws SQLException {
        RepairServicesTypes search = repairServicesTypesDAO.search(repairType);
        return new RepairServicesTypesDTO(search.getServiceType(), search.getServiceCost());
    }

    @Override
    public ObservableList<String> getRepairTypes() throws SQLException {
        ArrayList<RepairServicesTypes> all = repairServicesTypesDAO.getAll();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (RepairServicesTypes r : all
        ) {
            list.add(r.getServiceType());
        }
        return list;
    }
    @Override
    public ObservableList<String> getOnlyCustomerIds() throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (Customer c : all
        ) {
            ids.add(c.getCustomerId());
        }
        return ids;
    }
    public ArrayList<String> getRepairIdsFromRepairsInProgress() throws SQLException {
        return repairsInProgressDAO.getRepairsInProgressIds();
    }
    @Override
    public String generateRepairId() throws SQLException {
        return repairIdDAO.generateRepairId();
    }
    @Override
    public CustomerDTO searchCustomers(String customerId) throws SQLException {
        Customer search = customerDAO.search(customerId);
        return new CustomerDTO(search.getCustomerId(), search.getCustomerName(), search.getCustomerContactNo(), search.getCustomerAddress());
    }
    @Override
    public ArrayList<RepairDetailsDTO> getRepairDetails(String repairId) throws SQLException {
        ArrayList<RepairDetails> repairDetails = repairDetailsDAO.searchRepairDetailsAsAList(repairId);
        ArrayList<RepairDetailsDTO> repairDetailsDTOS = new ArrayList<>();
        for (RepairDetails r : repairDetails
        ) {
            repairDetailsDTOS.add(new RepairDetailsDTO(r.getRepairId(), r.getRepairCount(), r.getRepairItemDescription(), r.getRepairType(), r.getRepairPart(), r.getRepairCost()));
        }
        return repairDetailsDTOS;
    }
}
