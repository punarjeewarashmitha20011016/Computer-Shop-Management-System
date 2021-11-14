package bo.custom;

import bo.SuperBO;
import dto.AdminDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageAdminBO extends SuperBO {
    public boolean saveAdmin(AdminDTO dto) throws SQLException;

    public boolean updateAdmin(AdminDTO dto) throws SQLException;

    public boolean deleteAdmin(String adminId) throws SQLException;

    public AdminDTO searchAdmin(String adminId) throws SQLException;

    public String getAdminId() throws SQLException;

    public ArrayList<AdminDTO> getAll() throws SQLException;

    public boolean ifAdminIdExists(String adminId) throws SQLException;

}
