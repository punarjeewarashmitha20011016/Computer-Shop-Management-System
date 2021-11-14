package bo.custom.impl;

import bo.custom.AddRepairsBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.RepairIdDTO;
import dto.RepairListDetailsDTO;
import dto.RepairsInProgressDTO;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;

public class AddRepairsBOImpl implements AddRepairsBO {
    private RepairIdDAO repairIdDAO = (RepairIdDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.GENERATEREPAIRID);
    private RepairsInProgressDAO repairsInProgressDAO= (RepairsInProgressDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSINPROGRESS);
    private RepairDetailsDAO repairDetailsDAO= (RepairDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRDETAILS);
    private RepairServicesPartsDAO repairServicesPartsDAO= (RepairServicesPartsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSERVICESPARTS);
    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveRepairIdInRepairIdTable(RepairIdDTO dto) throws SQLException {
        return repairIdDAO.add(new RepairID(dto.repairId));
    }

    @Override
    public String generateRepairId() throws SQLException {
        return repairIdDAO.generateRepairId();
    }


    @Override
    public boolean deleteRepairId(String id) throws SQLException {
        return repairIdDAO.delete(id);
    }

    @Override
    public boolean saveRepairsInProgress(RepairsInProgressDTO dto, int count) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        boolean exists = repairsInProgressDAO.ifRepairsInProgressIdExists(dto.getRepairId());
        if (exists) {
            return false;
        }
        connection.setAutoCommit(false);
        RepairsInProgress repairsInProgress = new RepairsInProgress(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairEstimatedFinishingDate(), dto.getRepairCost());
        if (!repairsInProgressDAO.add(repairsInProgress)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (RepairListDetailsDTO r : dto.getListDetailsArrayList()
        ) {
            RepairDetails details = new RepairDetails(dto.getRepairId(), r.getRepairCount(), dto.getRepairItemDescription(), r.getRepairType(), r.getRepairPart(), r.getRepairCost());
            if (!repairDetailsDAO.add(details)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            System.out.println(count);
            if (r.getRepairPart() != null) {
                RepairServicesParts repairServicesParts = repairServicesPartsDAO.searchRepairServicesPartsByRepairPart(r.getRepairPart());
                System.out.println(repairServicesParts);
                repairServicesParts.setItemQty(repairServicesParts.getItemQty() - count);
                if (!repairServicesPartsDAO.update(repairServicesParts)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                Item item = itemDAO.searchByItemDescription(r.getRepairPart());
                item.setItemQty(item.getItemQty() - count);
                if (!itemDAO.update(item)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
