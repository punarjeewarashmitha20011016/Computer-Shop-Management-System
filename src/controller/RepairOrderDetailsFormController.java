package controller;

import bo.BOFactory;
import bo.custom.RepairOrderDetailsBO;
import dto.RepairOrderDetailsDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.RepairOrderDetailsTm;

import java.sql.SQLException;
import java.util.ArrayList;

import static controller.ViewOrderDetailsFormController.repairOrderId;

public class RepairOrderDetailsFormController {
    public AnchorPane repairOrderDetailsPane;
    public TableView<RepairOrderDetailsTm> tblRepairOrderDetailsView;
    public TableColumn tblOrderId;
    public TableColumn tblItemDescription;
    public TableColumn tblRepairType;
    public TableColumn tblRepairPart;
    public TableColumn tblRepairStartDate;
    public TableColumn tblRepairFinishedDate;
    public TableColumn tblRepairCost;
    public TableColumn tblRepairCount;
    private RepairOrderDetailsBO repairOrderDetailsBO = (RepairOrderDetailsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRORDERDETAILS);

    public void initialize() {
        tblOrderId.setCellValueFactory(new PropertyValueFactory<>("repairOrderId"));
        tblRepairCount.setCellValueFactory(new PropertyValueFactory<>("repairCount"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
        tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        tblRepairPart.setCellValueFactory(new PropertyValueFactory<>("repairPart"));
        tblRepairStartDate.setCellValueFactory(new PropertyValueFactory<>("repairStartDate"));
        tblRepairFinishedDate.setCellValueFactory(new PropertyValueFactory<>("repairFinishedDate"));
        tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));

        try {
            setDataToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblRepairOrderDetailsView.refresh();
    }

    public void setDataToTable() throws SQLException {
        tblRepairOrderDetailsView.getItems().clear();
        ArrayList<RepairOrderDetailsDTO> repairOrderDetailsByOrderId = repairOrderDetailsBO.getRepairOrderDetailsByOrderId(repairOrderId);
        for (RepairOrderDetailsDTO r : repairOrderDetailsByOrderId
        ) {
            tblRepairOrderDetailsView.getItems().add(new RepairOrderDetailsTm(r.getOrderId(), r.getRepairCount(), r.getRepairItemDescription(), r.getRepairType(), r.getRepairPart(), r.getRepairStartDate(), r.getRepairFinishedDate(), r.getRepairItemPrice()));
        }
    }
}
