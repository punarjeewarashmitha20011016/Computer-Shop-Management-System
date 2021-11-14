package bo.custom.impl;

import bo.custom.StockBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {
    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i : all
        ) {
            itemDTOS.add(new ItemDTO(i.getItemCode(), i.getItemDescription(), i.getItemBrand(), i.getItemCategory(), i.getItemRam(), i.getItemStorage(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice()));
        }
        return itemDTOS;
    }
}
