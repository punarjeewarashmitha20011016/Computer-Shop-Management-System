package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemBrandDAO;
import entity.ItemBrand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBrandDAOImpl implements ItemBrandDAO {
    @Override
    public boolean add(ItemBrand itemBrand) throws SQLException {
       return CrudUtil.executeUpdate("INSERT INTO itemBrands VALUES(?)",itemBrand.getItemBrand());
    }

    @Override
    public boolean update(ItemBrand itemBrand) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE itemBrands SET itemBrand=?",itemBrand.getItemBrand());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM itemBrands where itemBrand=?",s);
    }

    @Override
    public ArrayList<ItemBrand> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM itemBrands");
        ArrayList<ItemBrand>itemBrands=new ArrayList<>();
        while (rst.next()){
            itemBrands.add(new ItemBrand(rst.getString(1)));
        }return itemBrands;
    }

    @Override
    public ItemBrand search(String s) throws SQLException {
        return null;
    }
}
