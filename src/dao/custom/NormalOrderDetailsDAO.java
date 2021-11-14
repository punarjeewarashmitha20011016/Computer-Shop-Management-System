package dao.custom;

import dao.CrudDAO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NormalOrderDetailsDAO extends CrudDAO<OrderDetails,String> {
    public ArrayList<OrderDetails>getAllOrderDetailsSearchedById(String id) throws SQLException;
    public int getNormalOrderDetailsItemCount(String orderId) throws SQLException;
}
