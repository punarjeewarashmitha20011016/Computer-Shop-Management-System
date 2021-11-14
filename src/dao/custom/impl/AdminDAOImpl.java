package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.AdminDAO;
import entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static controller.LoginFormController.adminName;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean add(Admin admin) throws SQLException {
        return CrudUtil.executeUpdate("INSERT INTO adminLogin VALUES(?,?,?,?,?,?,?)", admin.getAdminId(), admin.getAdminName(), admin.getAdminNic(), admin.getAdminContactNo(), admin.getAdminUserName(), admin.getAdminPassword(), admin.getAdminImage());
    }

    @Override
    public boolean update(Admin admin) throws SQLException {
        return CrudUtil.executeUpdate("UPDATE adminLogin SET adminName=?,adminNic=?,adminContactNo=?,adminUserName=?,adminPassword=?,adminImage=? WHERE adminId=?", admin.getAdminName(), admin.getAdminNic(), admin.getAdminContactNo(), admin.getAdminUserName(), admin.getAdminPassword(), admin.getAdminImage(), admin.getAdminId());
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.executeUpdate("DELETE FROM adminLogin WHERE adminId=?", s);
    }

    @Override
    public ArrayList<Admin> getAll() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM adminLogin");
        ArrayList<Admin>admins=new ArrayList<>();
        while (rst.next()){
            admins.add(new Admin(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7)));
        }return admins;
    }

    @Override
    public Admin search(String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM adminLogin WHERE adminId=?", id);
        if (rst.next()){
            return new Admin(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
        }return null;
    }

    @Override
    public String getAdminId() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT adminId from adminLogin order by adminId desc limit 1");
        if (rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if (temp<=9){
                return "A-00"+temp;
            }else if (temp<=99){
                return "A-0"+temp;
            }else {
                return "A-"+temp;
            }
        }else {
            return "A-001";
        }
    }

    @Override
    public boolean ifAdminIdExists(String adminId) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM adminLogin WHERE adminId=?",adminId).next();
    }

    @Override
    public String getAdminAvatar() throws SQLException {
        ResultSet rst = CrudUtil.executeQuery("SELECT adminImage from adminLogin where adminName=?", adminName);
        while (rst.next()){
            return rst.getString(1);
        }return null;
    }

    @Override
    public boolean ifAdminUserNameExists(String userName) throws SQLException {
        return CrudUtil.executeQuery("SELECT * FROM adminLogin WHERE adminUserName=?",userName).next();
    }
}
