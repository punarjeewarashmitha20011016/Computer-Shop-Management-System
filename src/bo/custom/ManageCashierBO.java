package bo.custom;

import bo.SuperBO;
import dto.CashierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageCashierBO extends SuperBO {
    public boolean saveCashier(CashierDTO dto) throws SQLException;

    public boolean updateCashier(CashierDTO dto) throws SQLException;

    public boolean deleteCashier(String cashierId) throws SQLException;

    public CashierDTO searchCashier(String cashierId) throws SQLException;

    public String getCashierId() throws SQLException;

    public ArrayList<CashierDTO> getAll() throws SQLException;

    public boolean ifCashierIdExists(String cashierId) throws SQLException;

}
