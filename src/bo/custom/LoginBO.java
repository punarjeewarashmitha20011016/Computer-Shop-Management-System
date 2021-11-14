package bo.custom;

import bo.SuperBO;
import dto.AdminDTO;
import dto.CashierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO extends SuperBO {
    public boolean adminUserNameExists(String adminUserName) throws SQLException;
    public boolean cashierUserNameExists(String cashierUserName) throws SQLException;
    public ArrayList<AdminDTO>getAllAdmins() throws SQLException;
    public ArrayList<CashierDTO>getAllCashiers() throws SQLException;
}
