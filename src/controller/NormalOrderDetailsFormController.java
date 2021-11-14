package controller;

import bo.BOFactory;
import bo.custom.NormalOrderDetailsBO;
import dto.NormalOrderDetailsDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.OrderDetailsTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class NormalOrderDetailsFormController {
    public AnchorPane normalOrderItemDetailsPane;
    public TableView<OrderDetailsTm> tblNormalOrderItemDetailsView;
    public TableColumn tblOrderId;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblItemBrand;
    public TableColumn tblItemCategory;
    public TableColumn tblItemRam;
    public TableColumn tblItemStorage;
    public TableColumn tblItemQty;
    public TableColumn tblItemDiscount;
    public TableColumn tblItemPrice;
    private NormalOrderDetailsBO normalOrderDetailsBO = (NormalOrderDetailsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.NORMALORDERDETAILS);

    public void initialize() {
        tblOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        tblItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        tblItemRam.setCellValueFactory(new PropertyValueFactory<>("itemRam"));
        tblItemStorage.setCellValueFactory(new PropertyValueFactory<>("itemStorage"));
        tblItemQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItemDiscount.setCellValueFactory(new PropertyValueFactory<>("itemDiscount"));
        tblItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemCost"));

        try {
            setDateToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblNormalOrderItemDetailsView.refresh();

    }

    public void setDateToTable() throws SQLException {
        tblNormalOrderItemDetailsView.getItems().clear();
        ArrayList<NormalOrderDetailsDTO> allNormalOrderDetails = normalOrderDetailsBO.getAllNormalOrderDetails();
        for (NormalOrderDetailsDTO dto : allNormalOrderDetails
        ) {
            tblNormalOrderItemDetailsView.getItems().add(new OrderDetailsTm(dto.getOrderId(), dto.getItemCode(), dto.getItemDescription(), dto.getItemBrand(), dto.getItemCategory(), dto.getItemRam(), dto.getItemStorage(), dto.getQtyOnHand(), dto.getItemDiscount(), dto.getItemCost()));
        }
    }
}
