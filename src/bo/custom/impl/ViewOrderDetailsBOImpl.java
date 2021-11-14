package bo.custom.impl;

import bo.custom.ViewOrderDetailsBO;
import dao.DAOFactory;
import dao.custom.NormalOrderDAO;
import dao.custom.NormalOrderDetailsDAO;
import dao.custom.RepairOrderDAO;
import dao.custom.RepairOrderDetailsDAO;
import dto.NormalOrderDTO;
import dto.RepairOrderDTO;
import entity.Order;
import entity.RepairOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewOrderDetailsBOImpl implements ViewOrderDetailsBO {
    private NormalOrderDAO normalOrderDAO= (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private NormalOrderDetailsDAO normalOrderDetailsDAO= (NormalOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDERDETAILS);
    private RepairOrderDAO repairOrderDAO= (RepairOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDER);
    private RepairOrderDetailsDAO repairOrderDetailsDAO= (RepairOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDERDETAILS);
    @Override
    public NormalOrderDTO searchNormalOrders(String orderId) throws SQLException {
        Order search = normalOrderDAO.search(orderId);
        return new NormalOrderDTO(search.getOrderId(), search.getCustomerId(), search.getOrderDate(), search.getOrderTime(), search.getOrderCost());
    }

    @Override
    public int getNormalOrderDetailsItemCount(String id) throws SQLException {
        return normalOrderDetailsDAO.getNormalOrderDetailsItemCount(id);
    }

    @Override
    public RepairOrderDTO searchRepairOrders(String repairId) throws SQLException {
        RepairOrder search = repairOrderDAO.search(repairId);
        return new RepairOrderDTO(search.getOrderId(), search.getRepairId(), search.getCustomerId(), search.getOrderDate(), search.getOrderTime(), search.getOrderDiscount(), search.getOrderCost());
    }

    @Override
    public int getRepairOrderRepairsCount(String orderId) throws SQLException {
        return repairOrderDetailsDAO.getRepairOrdersRepairCounts(orderId);
    }

    @Override
    public ArrayList<String> getNormalOrderIds() throws SQLException {
        ArrayList<Order> all = normalOrderDAO.getAll();
        ArrayList<String>getIds=new ArrayList<>();
        for (Order r:all
        ) {
            getIds.add(r.getOrderId());
        }return getIds;
    }

    @Override
    public ArrayList<String> getRepairOrderIds() throws SQLException {
        ArrayList<RepairOrder> all = repairOrderDAO.getAll();
        ArrayList<String>ids=new ArrayList<>();
        for (RepairOrder r:all
             ) {
            ids.add(r.getOrderId());
        }return ids;
    }
}
