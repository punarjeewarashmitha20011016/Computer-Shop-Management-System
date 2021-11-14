package bo.custom.impl;

import bo.custom.LoginBO;
import dao.DAOFactory;
import dao.custom.AdminDAO;
import dao.custom.CashierDAO;
import dto.AdminDTO;
import dto.CashierDTO;
import entity.Admin;
import entity.Cashier;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl implements LoginBO {
    private AdminDAO adminDAO= (AdminDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ADMIN);
    private CashierDAO cashierDAO= (CashierDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CASHIER);
    @Override
    public boolean adminUserNameExists(String adminUserName) throws SQLException {
        return adminDAO.ifAdminUserNameExists(adminUserName);
    }

    @Override
    public boolean cashierUserNameExists(String cashierUserName) throws SQLException {
        return cashierDAO.ifCashierUserNameExists(cashierUserName);
    }

    @Override
    public ArrayList<AdminDTO> getAllAdmins() throws SQLException {
        ArrayList<Admin> all = adminDAO.getAll();
        ArrayList<AdminDTO>getAll=new ArrayList<>();
        for (Admin a:all
             ) {
            getAll.add(new AdminDTO(a.getAdminId(),a.getAdminName(),a.getAdminNic(),a.getAdminContactNo(),a.getAdminUserName(),a.getAdminPassword(),a.getAdminImage()));
        }return getAll;
    }

    @Override
    public ArrayList<CashierDTO> getAllCashiers() throws SQLException {
        ArrayList<Cashier> all = cashierDAO.getAll();
        ArrayList<CashierDTO>getAll=new ArrayList<>();
        for (Cashier c:all
             ) {
            getAll.add(new CashierDTO(c.getCashierId(),c.getCashierName(),c.getCashierNic(),c.getCashierContactNo(),c.getCashierUserName(),c.getCashierPassword(),c.getImageLocation()));
        }return getAll;
    }
}
