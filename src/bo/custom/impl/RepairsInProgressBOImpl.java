package bo.custom.impl;

import bo.custom.RepairsInProgressBO;
import dao.DAOFactory;
import dao.custom.RepairDetailsDAO;
import dao.custom.RepairsFinishedDAO;
import dao.custom.RepairsFinishedDetailsDAO;
import dao.custom.RepairsInProgressDAO;
import db.DbConnection;
import dto.RepairDetailsDTO;
import dto.RepairListDetailsDTO;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;
import entity.RepairDetails;
import entity.RepairsFinished;
import entity.RepairsFinishedDetails;
import entity.RepairsInProgress;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsInProgressBOImpl implements RepairsInProgressBO {
    private RepairsInProgressDAO repairsInProgressDAO= (RepairsInProgressDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSINPROGRESS);
    private RepairDetailsDAO repairDetailsDAO= (RepairDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRDETAILS);
    private RepairsFinishedDAO repairsFinishedDAO= (RepairsFinishedDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHED);
    private RepairsFinishedDetailsDAO repairsFinishedDetailsDAO= (RepairsFinishedDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHEDETAILS);
    @Override
    public ArrayList<String> getRepairIdsFromRepairsInProgress() throws SQLException {
        return repairsInProgressDAO.getRepairsInProgressIds();
    }
    @Override
    public ArrayList<RepairsInProgressDTO> getAllFromRepairsInProgress() throws SQLException {
        ArrayList<RepairsInProgress> all = repairsInProgressDAO.getAll();
        ArrayList<RepairsInProgressDTO> dto = new ArrayList<>();
        for (RepairsInProgress r : all) {
            dto.add(new RepairsInProgressDTO(r.getCustomerId(), r.getRepairId(), r.getRepairItemDescription(), r.getRepairStartDate(), r.getRepairEstimatedFinishingDate(), r.getRepairCost()));
        }
        return dto;
    }

    @Override
    public RepairsInProgressDTO searchRepairsInProgress(String repairId) throws SQLException {
        RepairsInProgress search = repairsInProgressDAO.search(repairId);
        return new RepairsInProgressDTO(search.getCustomerId(), search.getRepairId(), search.getRepairItemDescription(), search.getRepairStartDate(), search.getRepairEstimatedFinishingDate(), search.getRepairCost());
    }

    @Override
    public boolean deleteRepairsInProgress(String repairId) throws SQLException {
        return repairsInProgressDAO.delete(repairId);
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
    @Override
    public boolean saveRepairsFinished(RepairsFinishedDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        boolean exists = repairsFinishedDAO.ifRepairsFinishedExists(dto.getRepairId());
        if (exists) {
            return false;
        }
        connection.setAutoCommit(false);
        RepairsFinished repairsFinished = new RepairsFinished(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairFinishedDate(), dto.getRepairCost());
        if (!repairsFinishedDAO.add(repairsFinished)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (RepairListDetailsDTO details : dto.getListDetailsArrayList()
        ) {
            RepairsFinishedDetails repairDetails = new RepairsFinishedDetails(dto.getRepairId(), details.getRepairCount(), dto.getRepairItemDescription(), details.getRepairType(), details.getRepairPart(), details.getRepairCost());
            if (!repairsFinishedDetailsDAO.add(repairDetails)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
