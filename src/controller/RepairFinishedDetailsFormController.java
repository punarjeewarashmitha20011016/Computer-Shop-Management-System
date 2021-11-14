package controller;

import bo.BOFactory;
import bo.custom.RepairsFinishedDetailsBO;
import dto.RepairsFinishedDetailsDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.RepairDetailsTm;

import java.sql.SQLException;
import java.util.ArrayList;

import static controller.RepairFinishedFromController.searchField;

/*import static controller.RepairFinishedFromController.txtSearchRepairsFromRepairsFinishedTable;*/

public class RepairFinishedDetailsFormController {
    public AnchorPane repairFinishedDetailsPane;
    public TableView<RepairDetailsTm> tblRepairFinishedDetailsView;
    public TableColumn tblRepairId;
    public TableColumn tblRepairItemDescription;
    public TableColumn tblRepairType;
    public TableColumn tblRepairPart;
    public TableColumn tblRepairUnitPrice;
    public TableColumn tblRepairCount;
    private RepairsFinishedDetailsBO repairsFinishedDetailsBO= (RepairsFinishedDetailsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRSFINISHEDDETAILS);

    public void initialize() {
        tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        tblRepairCount.setCellValueFactory(new PropertyValueFactory<>("repairCount"));
        tblRepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
        tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        tblRepairPart.setCellValueFactory(new PropertyValueFactory<>("repairPart"));
        tblRepairUnitPrice.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        try {
            setDataToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblRepairFinishedDetailsView.refresh();
    }

    public void setDataToTable() throws SQLException {
        tblRepairFinishedDetailsView.getItems().clear();
        ArrayList<RepairsFinishedDetailsDTO> allFinishedDetailsFromRepairId = repairsFinishedDetailsBO.getAllFinishedDetailsFromRepairId(searchField);
        for (RepairsFinishedDetailsDTO dto : allFinishedDetailsFromRepairId
        ) {
            tblRepairFinishedDetailsView.getItems().add(new RepairDetailsTm(dto.getRepairId(), dto.getRepairCount(), dto.getRepairItemDescription(), dto.getRepairType(), dto.getRepairPart(), dto.getRepairCost()));
        }

    }
}
