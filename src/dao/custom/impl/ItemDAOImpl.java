package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO Computers_Items VALUES(?,?,?,?,?,?,?,?,?)",
                item.getItemCode(),
                item.getItemDescription(), item.getItemBrand(), item
                        .getItemCategory(), item.getItemRam(), item.getItemStorage(), item.getItemQty(),
                item.getItemBuyingPrice(), item.getItemUnitPrice());
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.executeUpdate("Update Computers_Items SET itemDescription=?,itemBrand=?,itemCategory=?,itemRam=?,itemStorage=?,itemQty=?,itemBuyingPrice=?,itemUnitPrice=? where itemCode=?",
                item.getItemDescription(), item.getItemBrand(), item
                        .getItemCategory(), item.getItemRam(), item.getItemStorage(), item.getItemQty(),
                item.getItemBuyingPrice(), item.getItemUnitPrice(), item.getItemCode());
    }

    @Override
    public boolean delete(String itemCode) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM Computers_Items where itemCode=?", itemCode);
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Computers_Items");
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getDouble(6), rst.getInt(7), rst.getDouble(8), rst.getDouble(9)));
        }
        return items;
    }

    @Override
    public Item search(String itemCode) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Computers_Items where itemCode=?", itemCode);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getDouble(6), rst.getInt(7), rst.getDouble(8), rst.getDouble(9));
        }
        return null;
    }

    @Override
    public String getItemCode() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode from Computers_Items order by itemCode desc limit 1");
        if (rst.next()) {
            int temp = Integer.parseInt(rst.getString(1).split("-")[1]);
            temp = temp + 1;
            if (temp <= 9) {
                return "I-00" + temp;
            } else if (temp <= 99) {
                return "I-0" + temp;
            } else {
                return "I-" + temp;
            }
        } else {
            return "I-001";
        }
    }

    @Override
    public boolean ifItemExists(String itemCode) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM Computers_Items where itemCode=?", itemCode).next();
    }

    @Override
    public Item searchByItemDescription(String itemDescription) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Computers_Items where itemDescription=?",itemDescription);
        if (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5),rst.getDouble(6),rst.getInt(7),rst.getDouble(8),rst.getDouble(9));
        }return null;
    }

    @Override
    public ArrayList<Item> getLowestRemainingItemsInTheStock() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * from Computers_Items where itemQty <= 5");
        ArrayList<Item>items=new ArrayList<>();
        while (rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5),rst.getDouble(6),rst.getInt(7),rst.getDouble(8),rst.getDouble(9)));
        }return items;
    }

    @Override
    public String getMostSellingItem() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode,itemDescription,SUM(itemQtyOnHand)as itemQtyOnHand from NormalOrderDetails group by itemCode order by itemQtyOnHand desc limit 1");
        while (rst.next()){
            return rst.getString(2);
        }return null;
    }

    @Override
    public double getSumOfTotalItemBuyingPrice() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(itemBuyingPrice) FROM Computers_Items");
        while (rst.next()){
            return rst.getDouble(1);
        }return 0.0;
    }
}
