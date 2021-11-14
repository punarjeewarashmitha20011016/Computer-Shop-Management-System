package bo.custom.impl;

import bo.custom.RepairsFinishedDetailsBO;
import dao.DAOFactory;
import dao.custom.RepairsFinishedDetailsDAO;
import dto.RepairsFinishedDetailsDTO;
import entity.RepairsFinishedDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairsFinishedDetailsBOImpl implements RepairsFinishedDetailsBO {
    private RepairsFinishedDetailsDAO repairsFinishedDetailsDAO= (RepairsFinishedDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRSFINISHEDETAILS);
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
