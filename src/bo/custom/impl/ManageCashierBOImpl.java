package bo.custom.impl;

import bo.custom.ManageCashierBO;
import dao.DAOFactory;
import dao.custom.CashierDAO;
import dto.CashierDTO;
import entity.Cashier;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCashierBOImpl implements ManageCashierBO {
    private CashierDAO cashierDAO= (CashierDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.CASHIER);
    @Override

    public boolean saveCashier(CashierDTO dto) throws SQLException {
        return cashierDAO.add(new Cashier(dto.getCashierId(), dto.getCashierName(), dto.getCashierNic(), dto.getCashierContactNo(), dto.getCashierUserName(), dto.getCashierPassword(), dto.getImageLocation()));
    }

    @Override
    public boolean updateCashier(CashierDTO dto) throws SQLException {
        return cashierDAO.update(new Cashier(dto.getCashierId(), dto.getCashierName(), dto.getCashierNic(), dto.getCashierContactNo(), dto.getCashierUserName(), dto.getCashierPassword(), dto.getImageLocation()));
    }

    @Override
    public boolean deleteCashier(String cashierId) throws SQLException {
        return cashierDAO.delete(cashierId);
    }

    @Override
    public CashierDTO searchCashier(String cashierId) throws SQLException {
        Cashier search = cashierDAO.search(cashierId);
        return new CashierDTO(search.getCashierId(), search.getCashierName(), search.getCashierNic(), search.getCashierContactNo(), search.getCashierUserName(), search.getCashierPassword(), search.getImageLocation());
    }

    @Override
    public String getCashierId() throws SQLException {
        return cashierDAO.getCashierId();
    }

    @Override
    public ArrayList<CashierDTO> getAll() throws SQLException {
        ArrayList<Cashier> all = cashierDAO.getAll();
        ArrayList<CashierDTO> cashierDTOS = new ArrayList<>();
        for (Cashier c : all
        ) {
            cashierDTOS.add(new CashierDTO(c.getCashierId(), c.getCashierName(), c.getCashierNic(), c.getCashierContactNo(), c.getCashierUserName(), c.getCashierPassword(), c.getImageLocation()));
        }
        return cashierDTOS;
    }

    @Override
    public boolean ifCashierIdExists(String cashierId) throws SQLException {
        return cashierDAO.ifCashierIdExists(cashierId);
    }
}
