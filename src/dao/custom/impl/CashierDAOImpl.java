package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CashierDAO;
import entity.Cashier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static controller.LoginFormController.cashierName;

public class CashierDAOImpl implements CashierDAO {
    @Override
    public boolean add(Cashier cashier) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO cashierLogin VALUES(?,?,?,?,?,?,?)",cashier.getCashierId(),cashier.getCashierName(),cashier.getCashierNic(),cashier.getCashierContactNo(),cashier.getCashierUserName(),cashier.getCashierPassword(),cashier.getImageLocation());
    }

    @Override
    public boolean update(Cashier cashier) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE cashierLogin SET cashierName=?,cashierNic=?,cashierContactNo=?,cashierUserName=?,cashierPassword=?,cashierImage=? WHERE cashierId=?",cashier.getCashierName(),cashier.getCashierNic(),cashier.getCashierContactNo(),cashier.getCashierUserName(),cashier.getCashierPassword(),cashier.getImageLocation(),cashier.getCashierId());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM cashierLogin WHERE cashierId=?",s);
    }

    @Override
    public ArrayList<Cashier> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM cashierLogin");
        ArrayList<Cashier>cashiers=new ArrayList<>();
        while (rst.next()){
            cashiers.add(new Cashier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7)));
        }
        return cashiers;
    }

    @Override
    public Cashier search(String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM cashierLogin WHERE cashierId=?", s);
        if (rst.next()){
            return new Cashier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
        }return null;
    }

    @Override
    public String getCashierId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cashierId from cashierLogin order by cashierId desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "CA-00"+temp;
            }else if (temp<=99){
                return "CA-0"+temp;
            }else {
                return "CA-"+temp;
            }
        }else {
            return "CA-001";
        }
    }

    @Override
    public boolean ifCashierIdExists(String cashierId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM cashierLogin WHERE cashierId=?",cashierId).next();
    }

    @Override
    public String getCashierAvatar() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cashierImage from cashierlogin where cashierName=?", cashierName);
        while (rst.next()){
            return rst.getString(1);
        }return null;
    }

    @Override
    public boolean ifCashierUserNameExists(String userName) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM cashierLogin WHERE cashierUserName=?",userName).next();
    }
}
