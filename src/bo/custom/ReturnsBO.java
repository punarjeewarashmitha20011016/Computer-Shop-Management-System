package bo.custom;

import bo.SuperBO;
import dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnsBO extends SuperBO {
    public boolean saveReturns(ReturnsDTO returnsDTO) throws SQLException;

    public ArrayList<ReturnsDTO> getAllReturns() throws SQLException;

    public ArrayList<NormalOrderDetailsDTO> getAllNormalOrderDetails(String orderId) throws SQLException;

    public NormalOrderDTO searchNormalOrders(String orderId) throws SQLException;

    public ArrayList<String> getReturnIds() throws SQLException;

    public boolean updateIncome(double cost) throws SQLException;

    public String generateReturnId() throws SQLException;

    public ItemDTO searchItem(String itemCode) throws SQLException;

    public ArrayList<String> getNormalOrderIds() throws SQLException;

    public ArrayList<String>getItemCodes() throws SQLException;

}

