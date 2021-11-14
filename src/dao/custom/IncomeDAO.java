package dao.custom;

import dao.CrudDAO;
import entity.Income;

import java.sql.SQLException;

public interface IncomeDAO extends CrudDAO<Income,String> {
    public boolean updateNormalSalesIncome(Income income) throws SQLException;
    public boolean updateRepairSalesIncome(Income income) throws SQLException;
    public Income getIncomeAsAtToday(String income) throws SQLException;
    public boolean ifIncomeExists() throws SQLException;
}