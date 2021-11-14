package bo.custom.impl;

import bo.custom.RepairsFinishedBO;
import dao.DAOFactory;
import dao.custom.RepairsFinishedDAO;
import dao.custom.RepairsFinishedDetailsDAO;
import dto.RepairsFinishedDTO;
import dto.RepairsFinishedDetailsDTO;
import entity.RepairsFinished;
import entity.RepairsFinishedDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsFinishedBOImpl implements RepairsFinishedBO {
    private RepairsFinishedDAO repairsFinishedDAO= (RepairsFinishedDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHED);
    private RepairsFinishedDetailsDAO repairsFinishedDetailsDAO= (RepairsFinishedDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHEDETAILS);
    @Override
    public ArrayList<String> getRepairIdsFromRepairsFinished() throws SQLException {
        ArrayList<RepairsFinished> all = repairsFinishedDAO.getAll();
        ArrayList<String> ids = new ArrayList<>();
        for (RepairsFinished r : all
        ) {
            ids.add(r.getRepairId());
        }
        return ids;
    }

    @Override
    public ArrayList<RepairsFinishedDTO> getAllRepairsFinished() throws SQLException {
        ArrayList<RepairsFinished> all = repairsFinishedDAO.getAll();
        ArrayList<RepairsFinishedDTO> dto = new ArrayList<>();
        for (RepairsFinished r : all
        ) {
            dto.add(new RepairsFinishedDTO(r.getCustomerId(), r.getRepairId(), r.getRepairItemDescription(), r.getRepairStartDate(), r.getRepairFinishedDate(), r.getRepairCost()));
        }
        return dto;
    }
    @Override
    public ArrayList<RepairsFinishedDetailsDTO> getAllFinishedDetailsFromRepairId(String repairId) throws SQLException {
        ArrayList<RepairsFinishedDetails> allFromRepairId = repairsFinishedDetailsDAO.getAllFromRepairId(repairId);
        ArrayList<RepairsFinishedDetailsDTO> all = new ArrayList<>();
        for (RepairsFinishedDetails r : allFromRepairId) {
            all.add(new RepairsFinishedDetailsDTO(r.getRepairId(), r.getRepairCount(), r.getRepairItemDescription(), r.getRepairType(), r.getRepairPart(), r.getRepairCost()));
        }
        return all;
    }
}
