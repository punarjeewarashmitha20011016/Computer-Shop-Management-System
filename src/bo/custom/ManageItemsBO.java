package bo.custom;

import bo.SuperBO;
import dto.ItemBrandDTO;
import dto.ItemCategoryDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageItemsBO extends SuperBO {
    public boolean saveItem(ItemDTO item) throws SQLException;
    public boolean updateItem(ItemDTO item) throws SQLException;
    public boolean deleteItem(String itemCode) throws SQLException;
    public ArrayList<ItemDTO>getAll() throws SQLException;
    public ItemDTO searchItem(String itemCode) throws SQLException;
    public String getItemCode() throws SQLException;
    public boolean ifItemExists(String itemCode) throws SQLException;
    public boolean saveItemBrand(ItemBrandDTO itemBrandDTO) throws SQLException;
    public boolean saveItemCategory(ItemCategoryDTO itemCategoryDTO) throws SQLException;
    public ArrayList<ItemBrandDTO>getItemBrands() throws SQLException;
    public ArrayList<ItemCategoryDTO>getItemCategories() throws SQLException;
}
