package controller;

import bo.BOFactory;
import bo.custom.RepairsFinishedBO;
import dto.RepairsFinishedDTO;
import dto.RepairsFinishedDetailsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;
import view.tm.RepairFinishedTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairFinishedFromController {
    public TableView<RepairFinishedTm> tblRepairsFinished;
    public TableColumn tblCustomerId;
    public TableColumn tblRepairId;
    public TableColumn tblRepairItemDescription;
    public TableColumn tblRepairStartDate;
    public TableColumn tblRepairFinishedDate;
    public TableColumn tblRepairCost;
    public TextField txtSearchRepairsFromRepairsFinishedTable;
    public static String searchField;

    private RepairsFinishedBO repairsFinishedBO = (RepairsFinishedBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRSFINISHED);
    ObservableList<RepairFinishedTm> repairFinishedObservableList = FXCollections.observableArrayList();
    public static ObservableList<RepairsFinishedDetailsDTO> repairDetails = FXCollections.observableArrayList();

    public ArrayList<String> repairIdList = new ArrayList<>();
    public AutoCompletionBinding<String> autoCompletionBinding;

    public void initialize() {
        try {
            setDataToRepairIdList();
            setAutoCompleteSuggestion();
            tblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
            tblRepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
            tblRepairStartDate.setCellValueFactory(new PropertyValueFactory<>("repairStartDate"));
            tblRepairFinishedDate.setCellValueFactory(new PropertyValueFactory<>("repairFinishedDate"));
            tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
            setObListData();
            tblRepairsFinished.refresh();
            searchTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToRepairIdList() throws SQLException {
        repairIdList = repairsFinishedBO.getRepairIdsFromRepairsFinished();
    }

    public void setAutoCompleteSuggestion() {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchRepairsFromRepairsFinishedTable, repairIdList);
        txtSearchRepairsFromRepairsFinishedTable.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        CommonFunctions.autoCompletionLearnWord(repairIdList, txtSearchRepairsFromRepairsFinishedTable.getText().trim(), autoCompletionBinding, txtSearchRepairsFromRepairsFinishedTable);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void btnAddToMakeRepairOrder(ActionEvent actionEvent) {
        int index = searchIndexOfRepairsFinishedObList(txtSearchRepairsFromRepairsFinishedTable.getText());
        try {
            ArrayList<RepairsFinishedDetailsDTO> allFinishedDetailsFromRepairId = repairsFinishedBO.getAllFinishedDetailsFromRepairId(txtSearchRepairsFromRepairsFinishedTable.getText());
            for (RepairsFinishedDetailsDTO details : allFinishedDetailsFromRepairId
            ) {
                repairDetails.add(new RepairsFinishedDetailsDTO(details.getRepairId(), details.getRepairCount(), details.getRepairItemDescription(), details.getRepairType(), details.getRepairPart(), details.getRepairCost()));
            }
            repairFinishedObservableList.remove(index);
            CommonFunctions.setNotificationSuccess("Added To Cart", "Successfully Added to the Repair Order Cart");
            txtSearchRepairsFromRepairsFinishedTable.clear();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ViewRepairFinishedDetails(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/RepairFinishedDetailsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setObListData() throws SQLException {
        tblRepairsFinished.getItems().clear();
        ArrayList<RepairsFinishedDTO> allRepairsFinished = repairsFinishedBO.getAllRepairsFinished();
        for (RepairsFinishedDTO dto : allRepairsFinished
        ) {
            repairFinishedObservableList.add(new RepairFinishedTm(dto.getCustomerId(), dto.getRepairId(), dto.getRepairItemDescription(), dto.getRepairStartDate(), dto.getRepairFinishedDate(), dto.getRepairCost()));
        }
        tblRepairsFinished.setItems(repairFinishedObservableList);
    }

    public void searchTable() {
        FilteredList<RepairFinishedTm> filteredData = new FilteredList<>(repairFinishedObservableList, p -> true);
        txtSearchRepairsFromRepairsFinishedTable.textProperty().addListener((observable, oldValue, newValue) -> {
            searchField = txtSearchRepairsFromRepairsFinishedTable.getText();
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
        SortedList<RepairFinishedTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblRepairsFinished.comparatorProperty());
        tblRepairsFinished.setItems(sortedData);
    }

    public int searchIndexOfRepairsFinishedObList(String repairId) {
        for (int i = 0; i < repairFinishedObservableList.size(); i++) {
            if (repairId.equals(repairFinishedObservableList.get(i).getRepairId())) {
                return i;
            }
        }
        return -1;
    }
}
