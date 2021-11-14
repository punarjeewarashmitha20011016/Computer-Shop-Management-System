package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    public String getItemCode() throws SQLException;
    public boolean ifItemExists(String itemCode) throws SQLException;
    public Item searchByItemDescription(String itemDescription) throws SQLException;
    public ArrayList<Item>getLowestRemainingItemsInTheStock() throws SQLException;
    public String getMostSellingItem() throws SQLException;
    public double getSumOfTotalItemBuyingPrice() throws SQLException;
}
