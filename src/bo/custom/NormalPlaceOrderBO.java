package bo.custom;

import bo.SuperBO;
import dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NormalPlaceOrderBO extends SuperBO {
    public boolean purchaseNormalOrder(NormalOrderDTO dto) throws SQLException;
    public String getNormalOrderId() throws SQLException;
    public ArrayList<String>getCustomerIds() throws SQLException;
    public ArrayList<String>getItemCodes() throws SQLException;
    public CustomerDTO searchCustomers(String customerId) throws SQLException;
    public ItemDTO searchAllItems(String itemCode) throws SQLException;
    public ArrayList<ItemDTO>getAllItems() throws SQLException;
    public boolean updateIncome(IncomeDTO dto) throws SQLException;
    public boolean saveIncome(IncomeDTO income) throws SQLException;
    public boolean ifIncomeExists() throws SQLException;
}
