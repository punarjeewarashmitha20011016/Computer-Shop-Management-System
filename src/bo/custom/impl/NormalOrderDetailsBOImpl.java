package bo.custom.impl;

import bo.custom.NormalOrderDetailsBO;
import dao.DAOFactory;
import dao.custom.NormalOrderDetailsDAO;
import dto.NormalOrderDetailsDTO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class NormalOrderDetailsBOImpl implements NormalOrderDetailsBO {
    private NormalOrderDetailsDAO normalOrderDetailsDAO = (NormalOrderDetailsDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.NORMALORDERDETAILS);

    @Override
    public ArrayList<NormalOrderDetailsDTO> getAllNormalOrderDetails() throws SQLException {
        ArrayList<OrderDetails> all = normalOrderDetailsDAO.getAll();
        ArrayList<NormalOrderDetailsDTO> getAll = new ArrayList<>();
        for (OrderDetails o : all
        ) {
            getAll.add(new NormalOrderDetailsDTO(o.getOrderId(), o.getItemCode(), o.getItemDescription(), o.getItemBrand(), o.getItemCategory(), o.getItemRam(), o.getItemStorage(), o.getQtyOnHand(), o.getItemDiscount(), o.getItemCost()));
        }
        return getAll;
    }
}
