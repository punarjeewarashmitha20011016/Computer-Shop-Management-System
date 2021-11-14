package bo.custom.impl;

import bo.custom.AddRepairServicesAndPartsBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.RepairServicesPartsDAO;
import dao.custom.RepairServicesTypesDAO;
import dto.ItemDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;
import entity.Item;
import entity.RepairServicesParts;
import entity.RepairServicesTypes;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddRepairServicesAndPartsBOImpl implements AddRepairServicesAndPartsBO {
    private RepairServicesPartsDAO repairServicesPartsDAO = (RepairServicesPartsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSERVICESPARTS);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    private RepairServicesTypesDAO repairServicesTypesDAO = (RepairServicesTypesDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSERVICESTYPE);
    @Override
    public boolean saveRepairServicesParts(RepairServicesPartsDTO dto) throws SQLException {
        return repairServicesPartsDAO.add(new RepairServicesParts(dto.getItemCode(), dto.getItemDescription(), dto.getItemQty(), dto.getItemUnitPrice()));
    }

    @Override
    public boolean deleteRepairServicesParts(String itemCode) throws SQLException {
        return repairServicesPartsDAO.delete(itemCode);
    }

    @Override
    public boolean ifItemExists(String itemCode) throws SQLException {
        return itemDAO.ifItemExists(itemCode);
    }

    @Override
    public boolean ifRepairServicePartExists(String itemCode) throws SQLException {
        return repairServicesPartsDAO.ifRepairServicePartExists(itemCode);
    }

    @Override
    public ArrayList<RepairServicesPartsDTO> getAllRepairServicesParts() throws SQLException {
        ArrayList<RepairServicesParts> all = repairServicesPartsDAO.getAll();
        ArrayList<RepairServicesPartsDTO> dtos = new ArrayList<>();
        for (RepairServicesParts e : all
        ) {
            dtos.add(new RepairServicesPartsDTO(e.getItemCode(), e.getItemDescription(), e.getItemQty(), e.getItemUnitPrice()));
        }
        return dtos;
    }

    @Override
    public ItemDTO searchItems(String itemCode) throws SQLException {
        Item search = itemDAO.search(itemCode);
        return new ItemDTO(search.getItemCode(), search.getItemDescription(), search.getItemQty(), search.getItemUnitPrice());
    }

    @Override
    public boolean saveRepairServiceType(RepairServicesTypesDTO dto) throws SQLException {
        return repairServicesTypesDAO.add(new RepairServicesTypes(dto.getServiceType(), dto.getServiceCost()));
    }

    @Override
    public boolean updateServiceType(RepairServicesTypesDTO dto) throws SQLException {
        return repairServicesTypesDAO.update(new RepairServicesTypes(dto.getServiceType(), dto.getServiceCost()));
    }

    @Override
    public boolean deleteRepairServiceType(String repairType) throws SQLException {
        return repairServicesTypesDAO.delete(repairType);
    }

    @Override
    public ArrayList<RepairServicesTypesDTO> getAllRepairServicesTypes() throws SQLException {
        ArrayList<RepairServicesTypes> all = repairServicesTypesDAO.getAll();
        ArrayList<RepairServicesTypesDTO> repairServicesTypesDTOS = new ArrayList<>();
        for (RepairServicesTypes r : all) {
            repairServicesTypesDTOS.add(new RepairServicesTypesDTO(r.getServiceType(), r.getServiceCost()));
        }
        return repairServicesTypesDTOS;
    }

    @Override
    public boolean ifRepairTypeExists(String repairType) throws SQLException {
        return repairServicesTypesDAO.ifRepairTypeExists(repairType);
    }
}
