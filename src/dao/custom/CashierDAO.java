package dao.custom;

import dao.CrudDAO;
import entity.Cashier;

import java.sql.SQLException;

public interface CashierDAO extends CrudDAO<Cashier,String> {
    public String getCashierId() throws SQLException;
    public boolean ifCashierIdExists(String cashierId) throws SQLException;
    public String getCashierAvatar() throws SQLException;
    public boolean ifCashierUserNameExists(String userName) throws SQLException;
}
