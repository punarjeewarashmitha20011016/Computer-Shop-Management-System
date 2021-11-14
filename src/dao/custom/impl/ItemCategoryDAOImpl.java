package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemCategoryDAO;
import entity.ItemCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCategoryDAOImpl implements ItemCategoryDAO {
    @Override
    public boolean add(ItemCategory itemCategory) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO itemCategory VALUES(?)",itemCategory.getItemCategory());
    }

    @Override
    public boolean update(ItemCategory itemCategory) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<ItemCategory> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM itemCategory");
        ArrayList<ItemCategory>itemCategories=new ArrayList<>();
        while (resultSet.next()){
            itemCategories.add(new ItemCategory(resultSet.getString(1)));
        }return itemCategories;
    }

    @Override
    public ItemCategory search(String s) throws SQLException {
        return null;
    }
}
