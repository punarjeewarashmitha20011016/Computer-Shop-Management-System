package controller;

import bo.BOFactory;
import bo.custom.AddRepairsBO;
import com.jfoenix.controls.JFXDatePicker;
import dto.RepairIdDTO;
import dto.RepairListDetailsDTO;
import dto.RepairsInProgressDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CommonFunctions;
import view.tm.AddRepairTm;

import java.sql.SQLException;
import java.util.ArrayList;

import static controller.RepairItemsFormController.*;

public class AddRepairsFormController {
    public AnchorPane addRepairs;
    public TableView<AddRepairTm> tblAddRepairs;
    public TableColumn tblCustomerId;
    public TableColumn tblRepairId;
    public TableColumn tblRepairItemDescription;
    public TableColumn tblRepairType;
    public TableColumn tblRepairPart;
    public TableColumn tblRepairCost;
    private AddRepairsBO addRepairsBO = (AddRepairsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.ADDREPAIRS);
    public static String repairIdStatic;

    public void initialize() {
        setDataToTable();
        setObList();
    }

    public void setObList() {
        tblAddRepairs.getItems().clear();
        for (AddRepairTm repairs : addRepairObservableListStatic
        ) {
            tblAddRepairs.getItems().add(new AddRepairTm(repairs.getCustomerId(), repairs.getRepairId(), repairs.getRepairCount(), repairs.getRepairItemDescription(), repairs.getRepairItemType(), repairs.getRepairItemPart(), repairs.getRepairCost()));
        }
    }

    public void setDataToTable() {
        tblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        tblRepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
        tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairItemType"));
        tblRepairPart.setCellValueFactory(new PropertyValueFactory<>("repairItemPart"));
        tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        tblAddRepairs.refresh();
    }

    public void btnAddRepairToProgress(ActionEvent actionEvent) {
        RepairIdDTO dto = new RepairIdDTO(addRepair.getRepairId());
        try {
            if (addRepairsBO.saveRepairIdInRepairIdTable(dto)) {
                System.out.println("RepairId Successfully Saved");
            } else {
                System.out.println("Try Again");
                return;
            }
            ArrayList<RepairListDetailsDTO> listDetails = new ArrayList<>();
            int count = 0;
            for (AddRepairTm repair : tblAddRepairs.getItems()
            ) {
                listDetails.add(new RepairListDetailsDTO(repair.getRepairCount(), repair.getRepairItemType(), repair.getRepairItemPart(), repair.getRepairCost()));
                if (repair.getRepairItemPart() != null) {
                    count++;
                }
                System.out.println(listDetails);
            }
            double repairTotalCost = calculateTotalCostOfTheRepair();
            RepairsInProgressDTO repairsInProgress = new RepairsInProgressDTO(addRepair.getCustomerId(), addRepair.getRepairId(), addRepair.getRepairItemDescription(), startDate, estimatedFinishedDate, repairTotalCost, listDetails);

            if (addRepairsBO.saveRepairsInProgress(repairsInProgress, count)) {
                CommonFunctions.setNotificationSuccess("Repair Added", "Repair Added Successfully");
                tblAddRepairs.getItems().clear();
                repairCountChecker = 1;
                setClearFieldsInTheRepairItemsController();
                setDisableFieldsInTheRepairItemsController();
                getRepairId();
            } else {
                addRepairsBO.deleteRepairId(dto.repairId);
                CommonFunctions.setNotificationWarning("Try Again", "Adding Repair is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setClearFieldsInTheRepairItemsController() {
        ArrayList<TextField> fields = textFieldArrayList;
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).clear();
        }
        ArrayList<JFXDatePicker> datePickers = fieldsList;
        for (int i = 0; i < datePickers.size(); i++) {
            datePickers.get(i).setValue(null);
        }
        for (int i = 0; i < comboBoxArrayList.size(); i++) {
            if (comboBoxArrayList.get(i).getSelectionModel().getSelectedIndex() > -1) {
                comboBoxArrayList.get(i).getSelectionModel().clearSelection();
            }
        }
    }

    public void setDisableFieldsInTheRepairItemsController() {
        ArrayList<TextField> fields = textFieldArrayList;
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setDisable(true);
        }
        ArrayList<JFXDatePicker> datePickers = fieldsList;
        for (int i = 0; i < datePickers.size(); i++) {
            datePickers.get(i).setDisable(true);
        }
    }

    public void getRepairId() {
        try {
            repairIdStatic = addRepairsBO.generateRepairId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public double calculateTotalCostOfTheRepair() {
        ObservableList<AddRepairTm> addRepairList = tblAddRepairs.getItems();
        double repairCost = 0.0;
        for (int i = 0; i < addRepairList.size(); i++) {
            repairCost += addRepairList.get(i).getRepairCost();
        }
        return repairCost;
    }
}
