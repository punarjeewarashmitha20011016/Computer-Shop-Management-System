package bo.custom.impl;

import bo.custom.AdminBO;
import dao.DAOFactory;
import dao.custom.AdminDAO;
import dao.custom.ItemDAO;
import dao.custom.NormalOrderDAO;
import dao.custom.RepairOrderDAO;
import dto.AdminDTO;
import dto.ItemDTO;
import entity.Admin;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBOImpl implements AdminBO {
    private AdminDAO adminDAO= (AdminDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ADMIN);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    private NormalOrderDAO normalOrderDAO = (NormalOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDER);
    private RepairOrderDAO repairOrderDAO= (RepairOrderDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.REPAIRORDER);

    @Override
    public ArrayList<ItemDTO> getLowestRemainingItemsInTheStock() throws SQLException {
        ArrayList<Item> lowestRemainingItemsInTheStock = itemDAO.getLowestRemainingItemsInTheStock();
        ArrayList<ItemDTO> items = new ArrayList<>();
        for (Item i : lowestRemainingItemsInTheStock
        ) {
            items.add(new ItemDTO(i.getItemCode(), i.getItemDescription(), i.getItemBrand(), i.getItemCategory(), i.getItemRam(), i.getItemStorage(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice()));
        }
        return items;
    }

    @Override
    public int getAllNormalOrdersCount() throws SQLException {
        return normalOrderDAO.getAllOrdersCount();
    }

    @Override
    public int getAllRepairOrdersCount() throws SQLException {
        return repairOrderDAO.getAllRepairOrdersCount();
    }

    @Override
    public int getTotalOrdersPerDay() throws SQLException {
        return normalOrderDAO.getTotalOrdersPerDay();
    }

    @Override
    public int getTotalRepairOrdersPerDay() throws SQLException {
        return repairOrderDAO.getTotalRepairOrdersPerDay();
    }

    @Override
    public String getMostSellingItem() throws SQLException {
        return itemDAO.getMostSellingItem();
    }

    @Override
    public double getDailyNormalOrderIncome() throws SQLException {
        return normalOrderDAO.getDailyNormalOrderIncome();
    }

    @Override
    public double getDailyRepairOrderIncome() throws SQLException {
        return repairOrderDAO.getDailyRepairOrderIncome();
    }

    @Override
    public String getAdminAvatar() throws SQLException {
        return adminDAO.getAdminAvatar();
    }
    @Override
    public double getTotalNormalOrdersIncome() throws SQLException {
        return normalOrderDAO.getSumOfTotalNormalOrdersIncome();
    }

    @Override
    public double getTotalRepairSalesIncome() throws SQLException {
        return repairOrderDAO.getTotalRepairOrdersIncome();
    }
}
