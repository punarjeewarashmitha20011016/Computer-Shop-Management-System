package bo.custom.impl;

import bo.custom.ReturnsBO;
import dao.DAOFactory;
import dao.custom.*;
import dto.ItemDTO;
import dto.NormalOrderDTO;
import dto.NormalOrderDetailsDTO;
import dto.ReturnsDTO;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static util.CommonFunctions.setDate;

public class ReturnsBOImpl implements ReturnsBO {
    private ReturnsDAO returnsDAO = (ReturnsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.RETURNS);
    private NormalOrderDAO normalOrderDAO = (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private NormalOrderDetailsDAO normalOrderDetailsDAO = (NormalOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDERDETAILS);
    private IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.INCOME);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveReturns(ReturnsDTO returnsDTO) throws SQLException {
        if (!returnsDAO.ifReturnExists(returnsDTO.getReturnId())) {
            return returnsDAO.add(new Returns(returnsDTO.getReturnId(), returnsDTO.getOrderId(), returnsDTO.getCustomerId(), returnsDTO.getItemCode(), returnsDTO.getItemDescription(), returnsDTO.getReturnQty(), returnsDTO.getReturnReason(), returnsDTO.getItemPrice()));
        }
        return false;
    }

    @Override
    public ArrayList<ReturnsDTO> getAllReturns() throws SQLException {
        ArrayList<Returns> all = returnsDAO.getAll();
        ArrayList<ReturnsDTO> getAll = new ArrayList<>();
        for (Returns r : all
        ) {
            getAll.add(new ReturnsDTO(r.getReturnId(), r.getOrderId(), r.getCustomerId(), r.getItemCode(), r.getItemDescription(), r.getReturnQty(), r.getReturnReason(), r.getItemPrice()));
        }
        return getAll;

    }

    @Override
    public ArrayList<NormalOrderDetailsDTO> getAllNormalOrderDetails(String orderId) throws SQLException {
        ArrayList<OrderDetails> all = normalOrderDetailsDAO.getAllOrderDetailsSearchedById(orderId);
        ArrayList<NormalOrderDetailsDTO> getAll = new ArrayList<>();
        for (OrderDetails dto : all
        ) {
            getAll.add(new NormalOrderDetailsDTO(dto.getOrderId(), dto.getItemCode(), dto.getItemDescription(), dto.getItemBrand(), dto.getItemCategory(), dto.getItemRam(), dto.getItemStorage(), dto.getQtyOnHand(), dto.getItemDiscount(), dto.getItemCost()));
        }
        return getAll;
    }

    @Override
    public NormalOrderDTO searchNormalOrders(String orderId) throws SQLException {
        Order search = normalOrderDAO.search(orderId);
        return new NormalOrderDTO(search.getOrderId(), search.getCustomerId(), search.getOrderDate(), search.getOrderTime(), search.getOrderCost());
    }

    @Override
    public ArrayList<String> getReturnIds() throws SQLException {
        return returnsDAO.getOnlyReturnIds();
    }

    @Override
    public boolean updateIncome(double cost) throws SQLException {
        Income incomeAsAtToday = incomeDAO.getIncomeAsAtToday(setDate);
        incomeAsAtToday.setNormalSalesIncome(incomeAsAtToday.getNormalSalesIncome() - cost);
        incomeAsAtToday.setTotalIncome(incomeAsAtToday.getTotalIncome() - cost);
        if (incomeDAO.updateNormalSalesIncome(incomeAsAtToday)) {
            return true;
        }
        return false;
    }

    @Override
    public String generateReturnId() throws SQLException {
        return returnsDAO.generateReturnId();
    }

    @Override
    public ItemDTO searchItem(String itemCode) throws SQLException {
        Item search = itemDAO.search(itemCode);
        return new ItemDTO(search.getItemCode(), search.getItemDescription(), search.getItemBrand(), search.getItemCategory(), search.getItemRam(), search.getItemStorage(), search.getItemQty(), search.getItemBuyingPrice(), search.getItemUnitPrice());
    }

    @Override
    public ArrayList<String> getNormalOrderIds() throws SQLException {
        ArrayList<Order> all = normalOrderDAO.getAll();
        ArrayList<String> getIds = new ArrayList<>();
        for (Order order : all
        ) {
            getIds.add(order.getOrderId());
        }
        return getIds;
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<String>itemCodes=new ArrayList<>();
        for (Item dto : all){
            itemCodes.add(dto.getItemCode());
        }return itemCodes;
    }

}

