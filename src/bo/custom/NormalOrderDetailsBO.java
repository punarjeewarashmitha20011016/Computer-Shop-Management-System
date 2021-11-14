package bo.custom;

import bo.SuperBO;
import dto.NormalOrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NormalOrderDetailsBO extends SuperBO {
    public ArrayList<NormalOrderDetailsDTO> getAllNormalOrderDetails() throws SQLException;
}
