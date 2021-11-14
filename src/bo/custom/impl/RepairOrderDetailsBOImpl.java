package bo.custom.impl;

import bo.custom.RepairOrderDetailsBO;
import dao.DAOFactory;
import dao.custom.RepairOrderDetailsDAO;
import dto.RepairOrderDetailsDTO;
import entity.RepairOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairOrderDetailsBOImpl implements RepairOrderDetailsBO {
    private RepairOrderDetailsDAO repairOrderDetailsDAO= (RepairOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDERDETAILS);
    @Override
    public ArrayList<RepairOrderDetailsDTO> getRepairOrderDetailsByOrderId(String id) throws SQLException {
        ArrayList<RepairOrderDetails> repairOrderDetailsByOrderId = repairOrderDetailsDAO.getRepairOrderDetailsByOrderId(id);
        ArrayList<RepairOrderDetailsDTO>getAll=new ArrayList<>();
        for (RepairOrderDetails r:repairOrderDetailsByOrderId
        ) {
            getAll.add(new RepairOrderDetailsDTO(r.getOrderId(),r.getRepairCount(),r.getRepairItemDescription(),r.getRepairType(),r.getRepairPart(),r.getRepairStartDate(),r.getRepairFinishedDate(),r.getRepairItemPrice()));
        }return getAll;
    }
}
