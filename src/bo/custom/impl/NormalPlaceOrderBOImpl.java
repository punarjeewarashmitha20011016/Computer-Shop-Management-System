package bo.custom.impl;

import bo.custom.NormalPlaceOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.*;
import entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static util.CommonFunctions.setDate;

public class NormalPlaceOrderBOImpl implements NormalPlaceOrderBO {
    private NormalOrderDAO normalOrderDAO = (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private NormalOrderDetailsDAO normalOrderDetailsDAO = (NormalOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDERDETAILS);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CUSTOMER);
    private IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.INCOME);

    @Override
    public boolean purchaseNormalOrder(NormalOrderDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        boolean exists = normalOrderDAO.ifNormalOrderExists(dto.getOrderId());
        if (exists) {
            return false;
        }
        connection.setAutoCommit(false);
        Order order = new Order(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate(), dto.getOrderTime(), dto.getOrderCost());
        if (!normalOrderDAO.add(order)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (ItemDetailsDTO i : dto.getItems()
        ) {
            OrderDetails orderDetails = new OrderDetails(dto.getOrderId(), i.getItemCode(), i.getItemDescription(), i.getItemBrand(), i.getItemCategory(), i.getItemRam(), i.getItemStorage(), i.getItemQtyOnHand(), i.getItemDiscount(), i.getItemPrice());
            if (!normalOrderDetailsDAO.add(orderDetails)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            Item search = itemDAO.search(i.getItemCode());
            search.setItemQty(search.getItemQty() - i.getItemQtyOnHand());
            if (!itemDAO.update(search)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public String getNormalOrderId() throws SQLException {
        return normalOrderDAO.getOrderId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<String> customerIds = new ArrayList<>();
        for (Customer c : all
        ) {
            customerIds.add(c.getCustomerId());
        }
        return customerIds;
    }

    @Override
    public ArrayList<String> getItemCodes() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<String> items = new ArrayList<>();
        for (Item i : all
        ) {
            items.add(i.getItemCode());
        }
        return items;
    }

    @Override
    public CustomerDTO searchCustomers(String customerId) throws SQLException {
        Customer search = customerDAO.search(customerId);
        return new CustomerDTO(search.getCustomerId(), search.getCustomerName(), search.getCustomerContactNo(), search.getCustomerAddress());
    }

    @Override
    public ItemDTO searchAllItems(String itemCode) throws SQLException {
        Item search = itemDAO.search(itemCode);
        return new ItemDTO(search.getItemCode(), search.getItemDescription(), search.getItemBrand(), search.getItemCategory(), search.getItemRam(), search.getItemStorage(), search.getItemQty(), search.getItemBuyingPrice(), search.getItemUnitPrice());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item d : all
        ) {
            itemDTOS.add(new ItemDTO(d.getItemCode(), d.getItemDescription(), d.getItemBrand(), d.getItemCategory(), d.getItemRam(), d.getItemStorage(), d.getItemQty(), d.getItemBuyingPrice(), d.getItemUnitPrice()));
        }
        return itemDTOS;
    }

    @Override
    public boolean updateIncome(IncomeDTO dto) throws SQLException {
        Income incomeAsAtToday = incomeDAO.getIncomeAsAtToday(setDate);
        incomeAsAtToday.setNormalSalesIncome(incomeAsAtToday.getNormalSalesIncome() + dto.getNormalSalesIncome());
        incomeAsAtToday.setTotalIncome(incomeAsAtToday.getTotalIncome() + dto.getTotalIncome());
        if (incomeDAO.updateNormalSalesIncome(incomeAsAtToday)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveIncome(IncomeDTO income) throws SQLException {
        return incomeDAO.add(new Income(income.getDateAsPerIncome(), income.getNormalSalesIncome(), income.getRepairSalesIncome(), income.getTotalIncome()));
    }

    @Override
    public boolean ifIncomeExists() throws SQLException {
        return incomeDAO.ifIncomeExists();
    }
}
