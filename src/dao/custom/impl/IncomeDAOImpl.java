package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.IncomeDAO;
import entity.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeDAOImpl implements IncomeDAO {
    @Override
    public boolean add(Income income) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO Income VALUES(?,?,?,?)",income.getDateAsPerIncome(),income.getNormalSalesIncome(),income.getRepairSalesIncome(),income.getTotalIncome());
    }

    @Override
    public boolean update(Income income) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Income> getAll() throws SQLException {
        return null;
    }

    @Override
    public Income search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean updateNormalSalesIncome(Income income) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE Income SET normalSalesIncome=?,totalIncome=? where dateAsPerIncome=?", income.getNormalSalesIncome(), income.getTotalIncome(),income.getDateAsPerIncome());
    }

    @Override
    public boolean updateRepairSalesIncome(Income income) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE Income SET repairSalesIncome=?,totalIncome=? where dateAsPerIncome=?", income.getRepairSalesIncome(), income.getTotalIncome(),income.getDateAsPerIncome());
    }

    @Override
    public Income getIncomeAsAtToday(String income) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Income where dateAsPerIncome=?",income);
        if (rst.next()) {
            return new Income(rst.getString(1), rst.getDouble(2), rst.getDouble(3), rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean ifIncomeExists() throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM Income").next();
    }
}
