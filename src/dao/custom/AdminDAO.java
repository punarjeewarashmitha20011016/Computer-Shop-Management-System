package dao.custom;

import dao.CrudDAO;
import entity.Admin;

import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin,String> {
    public String getAdminId() throws SQLException;
    public boolean ifAdminIdExists(String adminId) throws SQLException;
    public String getAdminAvatar() throws SQLException;
    public boolean ifAdminUserNameExists(String userName) throws SQLException;
}
