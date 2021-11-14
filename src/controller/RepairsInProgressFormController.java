package controller;

import bo.BOFactory;
import bo.custom.RepairsInProgressBO;
import dto.RepairDetailsDTO;
import dto.RepairListDetailsDTO;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;
import view.tm.RepairsInProgressTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RepairsInProgressFormController {

    public AnchorPane RepairsInProgressPane;
    public TableView<RepairsInProgressTm> tblRepairsInProgress;
    public TableColumn tblCustomerId;
    public TableColumn tblRepairId;
    public TableColumn tblRepairItemDescription;
    public TableColumn tblRepairStartDate;
    public TableColumn tblEstimatedFinishingDate;
    public TableColumn tblRepairCost;
    public TextField txtSearchRepairs;
    private final ObservableList<RepairsInProgressTm> repairsInProgressObservableList = FXCollections.observableArrayList();

    public ArrayList<String> repairIdList = new ArrayList<>();
    public AutoCompletionBinding<String> autoCompletionBinding;
    private RepairsInProgressBO repairsInProgressBO = (RepairsInProgressBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRSINPROGRESS);

    public void initialize() {
        try {
            tblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
            tblRepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
            tblRepairStartDate.setCellValueFactory(new PropertyValueFactory<>("repairStartDate"));
            tblEstimatedFinishingDate.setCellValueFactory(new PropertyValueFactory<>("repairEstimatedFinishingDate"));
            tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
            setDataToTable();
            setDataToRepairIdList();
            autoSuggestionForSearchField();
            searchFromTheTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToRepairIdList() throws SQLException {
        repairIdList = repairsInProgressBO.getRepairIdsFromRepairsInProgress();
    }

    public void autoSuggestionForSearchField() {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchRepairs, repairIdList);
        txtSearchRepairs.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        CommonFunctions.autoCompletionLearnWord(repairIdList, txtSearchRepairs.getText().trim(), autoCompletionBinding, txtSearchRepairs);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void searchFromTheTable() {
        FilteredList<RepairsInProgressTm> filteredData = new FilteredList<>(repairsInProgressObservableList, p -> true);

        txtSearchRepairs.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getCustomerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getRepairId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<RepairsInProgressTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblRepairsInProgress.comparatorProperty());
        tblRepairsInProgress.setItems(sortedData);
    }

    public void setDataToTable() throws SQLException {
        tblRepairsInProgress.getItems().clear();
        ArrayList<RepairsInProgressDTO> allFromRepairsInProgress = repairsInProgressBO.getAllFromRepairsInProgress();
        for (RepairsInProgressDTO dto : allFromRepairsInProgress
        ) {
            repairsInProgressObservableList.add(new RepairsInProgressTm(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairEstimatedFinishingDate(), dto.getRepairCost()));
        }
        tblRepairsInProgress.setItems(repairsInProgressObservableList);
    }

    public void btnFinishRepair(ActionEvent actionEvent) {
        String date = CommonFunctions.setDate;
        try {
            RepairsInProgressDTO repairsInProgresses = repairsInProgressBO.searchRepairsInProgress(txtSearchRepairs.getText());
            ArrayList<RepairDetailsDTO> repairDetailsList = repairsInProgressBO.getRepairDetails(txtSearchRepairs.getText());
            ArrayList<RepairListDetailsDTO> repairListDetailDTOS = new ArrayList<>();
            for (RepairDetailsDTO details : repairDetailsList
            ) {
                repairListDetailDTOS.add(new RepairListDetailsDTO(details.getRepairCount(), details.getRepairType(), details.getRepairPart(), details.getRepairCost()));
            }

            String repairId = repairsInProgresses.getRepairId();
            String customerId = repairsInProgresses.getCustomerId();
            String startDate = repairsInProgresses.getRepairStartDate();
            String itemDescription = repairsInProgresses.getRepairItemDescription();
            double repairCost = repairsInProgresses.getRepairCost();

            RepairsFinishedDTO repairFinished = new RepairsFinishedDTO(customerId, repairId, itemDescription, startDate, date, repairCost, repairListDetailDTOS);

            System.out.println(repairFinished.getCustomerId());
            for (int i = 0; i < repairListDetailDTOS.size(); i++) {
                System.out.println(repairListDetailDTOS.get(i));
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Add this Repair to Repairs Finished Section", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            String id = txtSearchRepairs.getText();
            int index = isExistRepairsInProgress(txtSearchRepairs.getText());
            if (buttonType.get().equals(ButtonType.YES)) {
                if (repairsInProgressBO.saveRepairsFinished(repairFinished)) {
                    repairsInProgressObservableList.remove(index);
                    if (repairsInProgressBO.deleteRepairsInProgress(id)) {
                        System.out.println(txtSearchRepairs.getText() + "Removed From Repairs In Progress");
                        txtSearchRepairs.clear();
                    } else {
                        System.out.println("Try Again removing unsuccessful");
                    }
                    CommonFunctions.setNotificationSuccess("Added To Repair Finished", "Adding to repair finished section successfully");
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Adding to Repair Finished Section is Unsuccessful");
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int isExistRepairsInProgress(String repairId) throws SQLException {
        for (int i = 0; i < repairsInProgressObservableList.size(); i++) {
            if (repairId.equals(repairsInProgressObservableList.get(i).getRepairId())) {
                System.out.println("Index : " + i);
                return i;
            }
        }
        return -1;
    }
}
