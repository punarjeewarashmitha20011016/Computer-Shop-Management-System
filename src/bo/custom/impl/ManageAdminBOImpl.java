package bo.custom.impl;

import bo.custom.ManageAdminBO;
import dao.DAOFactory;
import dao.custom.AdminDAO;
import dao.custom.ItemDAO;
import dao.custom.NormalOrderDAO;
import dao.custom.RepairOrderDAO;
import dto.AdminDTO;
import dto.ItemDTO;
import entity.Admin;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageAdminBOImpl implements ManageAdminBO {
    private AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAOTypes(DAOFactory.DAOTypes.ADMIN);

    @Override
    public boolean saveAdmin(AdminDTO dto) throws SQLException {
        return adminDAO.add(new Admin(dto.getAdminId(), dto.getAdminName(), dto.getAdminNic(), dto.getAdminContactNo(), dto.getAdminUserName(), dto.getAdminPassword(), dto.getAdminImage()));
    }

    @Override
    public boolean updateAdmin(AdminDTO dto) throws SQLException {
        return adminDAO.update(new Admin(dto.getAdminId(), dto.getAdminName(), dto.getAdminNic(), dto.getAdminContactNo(), dto.getAdminUserName(), dto.getAdminPassword(), dto.getAdminImage()));
    }

    @Override
    public boolean deleteAdmin(String adminId) throws SQLException {
        return adminDAO.delete(adminId);
    }

    @Override
    public AdminDTO searchAdmin(String adminId) throws SQLException {
        Admin search = adminDAO.search(adminId);
        return new AdminDTO(search.getAdminId(), search.getAdminName(), search.getAdminNic(), search.getAdminContactNo(), search.getAdminUserName(), search.getAdminPassword(), search.getAdminImage());
    }

    @Override
    public String getAdminId() throws SQLException {
        return adminDAO.getAdminId();
    }

    @Override
    public ArrayList<AdminDTO> getAll() throws SQLException {
        ArrayList<Admin> all = adminDAO.getAll();
        ArrayList<AdminDTO> adminDTOS = new ArrayList<>();
        for (Admin a : all
        ) {
            adminDTOS.add(new AdminDTO(a.getAdminId(), a.getAdminName(), a.getAdminNic(), a.getAdminContactNo(), a.getAdminUserName(), a.getAdminPassword(), a.getAdminImage()));
        }
        return adminDTOS;
    }

    @Override
    public boolean ifAdminIdExists(String adminId) throws SQLException {
        return adminDAO.ifAdminIdExists(adminId);
    }
}
