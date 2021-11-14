package controller;

import bo.BOFactory;
import bo.custom.RepairItemsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.RepairDetailsDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;
import util.Validator;
import view.tm.AddRepairTm;
import view.tm.RepairDetailsTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static controller.AddRepairsFormController.repairIdStatic;
import static util.Validator.textFields;
import static util.Validator.txtField;

public class RepairItemsFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContactNo;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtRepairId;
    public JFXTextField txtRepairItemDescription;
    public JFXDatePicker dtpRepairStartDate;
    public JFXDatePicker dtpRepairEstimatedFinishedDate;
    public JFXComboBox<String> cmbRepairType;
    public JFXComboBox<String> cmbRepairPart;
    public AnchorPane repairItemsChildPane;
    public JFXTextField txtRepairCost;
    public TableView<RepairDetailsTm> tblRepairDetails;
    public TableColumn tblRepairId;
    public TableColumn tblRepairType;
    public TableColumn tblRepairPart;
    public TableColumn tblRepairCost;
    public TextField txtSearchRepairDetails;
    public String searchText;
    public JFXButton addRepairBtnId;
    public TableColumn tblrepairItemDescription;
    public TableColumn tblRepairCount;
    public ObservableList<String> itemsRepairType = FXCollections.observableArrayList();
    public ObservableList<String> itemsRepairPart = FXCollections.observableArrayList();

    public LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public static ObservableList<AddRepairTm> addRepairObservableListStatic = FXCollections.observableArrayList();
    int index;
    int index1;
    double repairCost = 0.0;
    double cost = 0.0;
    double costOfItemPart = 0.0;
    public static AddRepairTm addRepair;
    public static String startDate;
    public static String estimatedFinishedDate;
    //---------------------------------------------------------------
    Pattern repairIdPattern = Pattern.compile("^(R-)[0-9]{3}$");
    Pattern repairItemDescriptionPattern = Pattern.compile("^[A-z0-9 ]+$");
    Pattern repairCostPattern = Pattern.compile("^[0-9]+[.]?[0-9]+$");
    //---------------------------------------------------------------

    public static ArrayList<TextField> textFieldArrayList = new ArrayList<>();
    public static ArrayList<JFXDatePicker> fieldsList = new ArrayList<>();
    public static ArrayList<JFXComboBox> comboBoxArrayList = new ArrayList<>();

    public ArrayList<String> repairIdsListToSearch = new ArrayList<>();
    public AutoCompletionBinding<String> autoCompletionBinding;
    private RepairItemsBO repairItemsBO = (RepairItemsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRITEMS);

    public void initialize() {
        textFields.clear();
        validateRepairFields();
        new Validator(map);
        try {
            getRepairId();
            setCustomerIdToComboBox();
            setDataToRepairPartCombo();
            setDataToRepairTypeComboBox();
            setDataToRepairIdList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //--------------------------------------------------------
        textFieldArrayList.add(txtCustomerName);
        textFieldArrayList.add(txtCustomerContactNo);
        textFieldArrayList.add(txtCustomerAddress);
        textFieldArrayList.add(txtRepairId);
        textFieldArrayList.add(txtRepairItemDescription);
        textFieldArrayList.add(txtRepairCost);
        fieldsList.add(dtpRepairStartDate);
        fieldsList.add(dtpRepairEstimatedFinishedDate);
        comboBoxArrayList.add(cmbCustomerId);
        comboBoxArrayList.add(cmbRepairPart);
        comboBoxArrayList.add(cmbRepairType);
        //-------------------------------------------------------

        cmbRepairType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            repairCost = cost;
            txtRepairCost.setText(String.valueOf(repairCost));
        });

        cmbRepairPart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            repairCost = cost + costOfItemPart;
            txtRepairCost.setText(String.valueOf(repairCost));
        });
        //-------------------------------------------------------

        autoCompleteSearchField();
        tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        tblRepairCount.setCellValueFactory(new PropertyValueFactory<>("repairCount"));
        tblrepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
        tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        tblRepairPart.setCellValueFactory(new PropertyValueFactory<>("repairPart"));
        tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
        tblRepairDetails.refresh();
    }

    public void setDataToRepairIdList() throws SQLException {
        repairIdsListToSearch = repairItemsBO.getRepairIdsFromRepairsInProgress();
    }

    public void autoCompleteSearchField() {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchRepairDetails, repairIdsListToSearch);
        txtSearchRepairDetails.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    loadRepairsInProgressDetailsToTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                switch (event.getCode()) {
                    case SPACE:
                        CommonFunctions.autoCompletionLearnWord(repairIdsListToSearch, txtSearchRepairDetails.getText().trim(), autoCompletionBinding, txtSearchRepairDetails);
                        break;
                    case BACK_SPACE:
                        tblRepairDetails.getItems().clear();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void validateRepairFields() {
        map.put(txtRepairId, repairIdPattern);
        map.put(txtRepairItemDescription, repairItemDescriptionPattern);
        map.put(txtRepairCost, repairCostPattern);
    }

    public void setDataToRepairTypeComboBox() throws SQLException {
        itemsRepairType = repairItemsBO.getRepairTypes();
        cmbRepairType.setItems(itemsRepairType);
    }

    public void setDataToRepairPartCombo() throws SQLException {
        itemsRepairPart = repairItemsBO.getRepairParts();
        cmbRepairPart.setItems(itemsRepairPart);
    }

    public void getRepairId() throws SQLException {
        String repairIdText = repairItemsBO.generateRepairId();
        txtRepairId.setText(repairIdText);
    }

    public void setCustomerIdToComboBox() throws SQLException {
        cmbCustomerId.getItems().clear();
        ObservableList<String> onlyCustomerIds = repairItemsBO.getOnlyCustomerIds();
        cmbCustomerId.setItems(onlyCustomerIds);

    }

    public void setCustomerDetailsToFields(ActionEvent actionEvent) {
        System.out.println(repairIdStatic);
        if (repairIdStatic != null) {
            txtRepairId.setText(repairIdStatic);
        }
        setEnableFields();
        if (cmbCustomerId.getValue() != null) {
            String customerId = cmbCustomerId.getValue().toString();
            System.out.println(customerId);
            try {
                CustomerDTO customerDTO = repairItemsBO.searchCustomers(customerId);
                txtCustomerName.setText(customerDTO.getCustomerName());
                txtCustomerContactNo.setText(String.valueOf(customerDTO.getCustomerContactNo()));
                txtCustomerAddress.setText(customerDTO.getCustomerAddress());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static int repairCountChecker = 1;

    public void btnAddRepair(ActionEvent actionEvent) {
        String customerId = cmbCustomerId.getValue();
        String repairId = txtRepairId.getText();
        startDate = dtpRepairStartDate.getValue().toString();
        estimatedFinishedDate = dtpRepairEstimatedFinishedDate.getValue().toString();
        String repairItemDescription = txtRepairItemDescription.getText();
        String repairType = cmbRepairType.getValue();

        String repairPart = null;
        if (cmbRepairPart.getSelectionModel().isSelected(-1)) {
            System.out.println("not selected");
            repairPart = null;
            addRepair = new AddRepairTm(customerId, repairId, repairCountChecker, repairItemDescription, repairType, repairPart, repairCost);
        } else {
            repairPart = cmbRepairPart.getValue();
            addRepair = new AddRepairTm(customerId, repairId, repairCountChecker, repairItemDescription, repairType, repairPart, repairCost);
        }


        repairCountChecker++;
        addRepairObservableListStatic.add(addRepair);
        txtRepairCost.clear();
        loadUi("AddRepairsForm");
    }

    public void clearFields() {
        if (!cmbRepairPart.getSelectionModel().isEmpty()) {
            cmbRepairPart.getSelectionModel().clearSelection();
        }
    }

    public void setEnableFields() {

        for (int i = 0; i < textFieldArrayList.size(); i++) {
            textFieldArrayList.get(i).setDisable(false);
        }
        for (int i = 0; i < fieldsList.size(); i++) {
            fieldsList.get(i).setDisable(false);
        }
    }

    public void btnMakeRepairOrder(ActionEvent actionEvent) {
        loadUi("MakeRepairOrderForm");
    }

    public void btnAddRepairServicesAndParts(ActionEvent actionEvent) {
        loadUi("AddRepairServicesAndPartsForm");
    }

    public void loadUi(String fileName) {
        URL resource = getClass().getResource("../view/" + fileName + ".fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
            repairItemsChildPane.getChildren().clear();
            repairItemsChildPane.getChildren().addAll(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnRepairsInProgress(ActionEvent actionEvent) {
        loadUi("RepairsInProgressForm");
    }

    public void btnRepairFinished(ActionEvent actionEvent) {
        loadUi("RepairFinishedForm");
    }

    public void selectRepairTypeCombo(ActionEvent actionEvent) {
        index = cmbRepairType.getSelectionModel().getSelectedIndex();
        String repairType = null;
        if (cmbRepairType.getValue() != null) {
            if (cmbRepairType.getSelectionModel().isSelected(index)) {
                repairType = cmbRepairType.getValue();
                System.out.println(repairType);
                try {
                    RepairServicesTypesDTO dto = repairItemsBO.searchRepairType(repairType);
                    cost = dto.getServiceCost();
                    System.out.println(cost);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                clearFields();
            }
        }
    }

    public void selectRepairPartCombo(ActionEvent actionEvent) {
        if (!cmbRepairPart.getSelectionModel().isEmpty()) {
            index1 = cmbRepairPart.getSelectionModel().getSelectedIndex();
            String repairPart = null;
            if (cmbRepairPart.getSelectionModel().isSelected(index1)) {
                repairPart = cmbRepairPart.getValue();
                System.out.println(repairPart);
                try {
                    RepairServicesPartsDTO dto = repairItemsBO.searchRepairServicesParts(repairPart);
                    double itemPartCost = dto.getItemUnitPrice();
                    costOfItemPart = itemPartCost;
                    System.out.println(costOfItemPart);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void addRepairEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(map, addRepairBtnId);
        if (response instanceof Boolean) {
            addRepairBtnId.setDisable(false);
            txtField.setDisable(false);
        }
        if (keyEvent.getCode() == KeyCode.TAB) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
    }

    public void loadRepairsInProgressDetailsToTable() throws SQLException {
        tblRepairDetails.getItems().clear();
        ArrayList<RepairDetailsDTO> repairDetails = repairItemsBO.getRepairDetails(txtSearchRepairDetails.getText());
        for (RepairDetailsDTO dto : repairDetails
        ) {
            tblRepairDetails.getItems().add(new RepairDetailsTm(dto.getRepairId(), dto.getRepairCount(), dto.getRepairItemDescription(), dto.getRepairType(), dto.getRepairPart(), dto.getRepairCost()));
        }
    }

    public void searchRepairDetails(KeyEvent keyEvent) {
        if (txtSearchRepairDetails.getLength() == 5) {
            searchText = txtSearchRepairDetails.getText();
            System.out.println(searchText);
            try {
                loadRepairsInProgressDetailsToTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

