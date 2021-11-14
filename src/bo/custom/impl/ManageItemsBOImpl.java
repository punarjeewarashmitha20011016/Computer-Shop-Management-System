package bo.custom.impl;

import bo.custom.ManageItemsBO;
import dao.DAOFactory;
import dao.custom.ItemBrandDAO;
import dao.custom.ItemCategoryDAO;
import dao.custom.ItemDAO;
import dto.ItemBrandDTO;
import dto.ItemCategoryDTO;
import dto.ItemDTO;
import entity.Item;
import entity.ItemBrand;
import entity.ItemCategory;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageItemsBOImpl implements ManageItemsBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    private ItemBrandDAO itemBrandDAO = (ItemBrandDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEMBRAND);
    private ItemCategoryDAO itemCategoryDAO = (ItemCategoryDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEMCATEGORY);

    @Override
    public boolean saveItem(ItemDTO item) throws SQLException {
        return itemDAO.add(new Item(item.getItemCode(), item.getItemDescription(), item.getItemBrand(), item.getItemCategory(), item.getItemRam(), item.getItemStorage(), item.getItemQty(), item.getItemBuyingPrice(), item.getItemUnitPrice()));
    }

    @Override
    public boolean updateItem(ItemDTO item) throws SQLException {
        return itemDAO.update(new Item(item.getItemCode(), item.getItemDescription(), item.getItemBrand(), item.getItemCategory(), item.getItemRam(), item.getItemStorage(), item.getItemQty(), item.getItemBuyingPrice(), item.getItemUnitPrice()));
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException {
        return itemDAO.delete(itemCode);
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i : all
        ) {
            itemDTOS.add(new ItemDTO(i.getItemCode(), i.getItemDescription(), i.getItemBrand(), i.getItemCategory(), i.getItemRam(), i.getItemStorage(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice()));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO searchItem(String itemCode) throws SQLException {
        Item search = itemDAO.search(itemCode);
        return new ItemDTO(search.getItemCode(), search.getItemDescription(), search.getItemBrand(), search.getItemCategory(), search.getItemRam(), search.getItemStorage(), search.getItemQty(), search.getItemBuyingPrice(), search.getItemUnitPrice());
    }

    @Override
    public String getItemCode() throws SQLException {
        return itemDAO.getItemCode();
    }

    @Override
    public boolean ifItemExists(String itemCode) throws SQLException {
        return itemDAO.ifItemExists(itemCode);
    }

    @Override
    public boolean saveItemBrand(ItemBrandDTO itemBrandDTO) throws SQLException {
        return itemBrandDAO.add(new ItemBrand(itemBrandDTO.getItemBrand()));
    }

    @Override
    public boolean saveItemCategory(ItemCategoryDTO itemCategoryDTO) throws SQLException {
        return itemCategoryDAO.add(new ItemCategory(itemCategoryDTO.getItemCategory()));
    }

    @Override
    public ArrayList<ItemBrandDTO> getItemBrands() throws SQLException {
        ArrayList<ItemBrand> all = itemBrandDAO.getAll();
        ArrayList<ItemBrandDTO> itemBrandDTOS = new ArrayList<>();
        for (ItemBrand i:all
             ) {
            itemBrandDTOS.add(new ItemBrandDTO(i.getItemBrand()));
        }return itemBrandDTOS;
    }

    @Override
    public ArrayList<ItemCategoryDTO> getItemCategories() throws SQLException {
        ArrayList<ItemCategory> all = itemCategoryDAO.getAll();
        ArrayList<ItemCategoryDTO>itemCategoryDTOS=new ArrayList<>();
        for (ItemCategory i:all
             ) {
            itemCategoryDTOS.add(new ItemCategoryDTO(i.getItemCategory()));
        }return itemCategoryDTOS;
    }
}
