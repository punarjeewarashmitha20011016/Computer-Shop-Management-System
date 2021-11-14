package controller;

import bo.BOFactory;
import bo.custom.AddRepairServicesAndPartsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.RepairServicesPartsDTO;
import dto.RepairServicesTypesDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.CommonFunctions;
import util.Validator;
import view.tm.RepairServicesPartsTm;
import view.tm.RepairTypeTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.Validator.textFields;
import static util.Validator.txtField;

public class AddRepairServicesAndPartsFormController {
    public AnchorPane repairServicesAndPartsPane;
    public JFXTextField txtRepairType;
    public JFXTextField txtRepairCost;
    public TableView<RepairTypeTm> tblRepairTypeView;
    public TableColumn tblRepairType;
    public TableColumn tblRepairTypeCost;
    public JFXTextField txtItemPartCode;
    public JFXTextField txtItemPartDescription;
    public JFXTextField txtItemPartQty;
    public JFXTextField txtItemPartUnitPrice;
    public TableView<RepairServicesPartsTm> tblItemPartView;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblItemQty;
    public TableColumn tblItemUnitPrice;
    public JFXButton addRepairTypeId;
    public JFXButton updateRepairTypeId;
    public JFXButton deleteRepairTypeId;

    LinkedHashMap<TextField, Pattern> linkedHashMap = new LinkedHashMap<>();
    Pattern repairTypePattern = Pattern.compile("^[A-z ]+$");
    Pattern repairTypeCostPattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");

    private int selectedIndex = -1;
    private int selectedIndexItemPart = -1;

    private AddRepairServicesAndPartsBO addRepairServicesAndPartsBO = (AddRepairServicesAndPartsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.ADDREPAIRSERVICESANDPARTS);

    public void initialize() {
        textFields.clear();
        validateRepairType();
        new Validator(linkedHashMap);
        try {
            setItemPartsTable();
            setRepairTypesToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        selectRepairPartRowFromTheTable();
        selectRepairTypeTableRow();


    }

    public void setRepairTypesToTable() throws SQLException {
        tblRepairTypeView.getItems().clear();
        tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        tblRepairTypeCost.setCellValueFactory(new PropertyValueFactory<>("repairTypeCost"));

        ArrayList<RepairServicesTypesDTO> allRepairServicesTypes = addRepairServicesAndPartsBO.getAllRepairServicesTypes();
        for (RepairServicesTypesDTO dto : allRepairServicesTypes) {
            tblRepairTypeView.getItems().add(new RepairTypeTm(dto.getServiceType(), dto.getServiceCost()));
        }
    }

    public void selectRepairTypeTableRow() {
        ObservableList<RepairTypeTm> repairTypeObservableList = tblRepairTypeView.getItems();
        tblRepairTypeView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex = (int) newValue;
            txtRepairType.setText(repairTypeObservableList.get(selectedIndex).getRepairType());
            txtRepairCost.setText(String.valueOf(repairTypeObservableList.get(selectedIndex).getRepairTypeCost()));
        });
    }

    public void validateRepairType() {
        linkedHashMap.put(txtRepairType, repairTypePattern);
        linkedHashMap.put(txtRepairCost, repairTypeCostPattern);
    }

    public void validateRepairTypeEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(linkedHashMap, addRepairTypeId);

        if (response instanceof Boolean) {
            addRepairTypeId.setDisable(false);
            txtField.setDisable(false);
        }
        if (response instanceof TextField) {
            if (keyEvent.getCode() == KeyCode.TAB) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
    }

    public void btnAddRepairType(ActionEvent actionEvent) {
        RepairServicesTypesDTO repairType = new RepairServicesTypesDTO(txtRepairType.getText(), Double.parseDouble(txtRepairCost.getText()));
        try {
            if (!addRepairServicesAndPartsBO.ifRepairTypeExists(txtRepairType.getText())) {
                if (addRepairServicesAndPartsBO.saveRepairServiceType(repairType)) {
                    CommonFunctions.setNotificationSuccess("Repair Type Added", "Adding repair type is successful");
                    tblRepairTypeView.getItems().add(new RepairTypeTm(repairType.getServiceType(), repairType.getServiceCost()));
                    clearRepairTypeFields();
                    setDataToRepairTypeComboBox();
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Adding Repair Type is Unsuccessful");
                }
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Already this type of a repair exists");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnUpdateRepairType(ActionEvent actionEvent) {
        RepairServicesTypesDTO repairType = new RepairServicesTypesDTO(txtRepairType.getText(), Double.parseDouble(txtRepairCost.getText()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Update Repair Type", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        int index = repairTypeIsExist(txtRepairType.getText());
        try {
            if (buttonType.get().equals(ButtonType.YES)) {
                if (addRepairServicesAndPartsBO.ifRepairTypeExists(txtRepairType.getText())) {
                    if (selectedIndex != -1) {
                        if (addRepairServicesAndPartsBO.updateServiceType(repairType)) {
                            CommonFunctions.setNotificationSuccess("Repair Type Updated", "Updating Repair Type is Successful");
                            tblRepairTypeView.getItems().remove(selectedIndexItemPart);
                            tblRepairTypeView.getItems().add(new RepairTypeTm(repairType.getServiceType(), repairType.getServiceCost()));
                            clearRepairTypeFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Updating repair type is Unsuccessful");
                        }
                    } else {
                        if (addRepairServicesAndPartsBO.updateServiceType(repairType)) {
                            CommonFunctions.setNotificationSuccess("Repair Type Updated", "Updating Repair Type is Successful");
                            tblRepairTypeView.getItems().remove(index);
                            tblRepairTypeView.getItems().add(new RepairTypeTm(repairType.getServiceType(), repairType.getServiceCost()));
                            clearRepairTypeFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Updating repair type is Unsuccessful");
                        }
                    }
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnDeleteRepairType(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete Repair Type", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            int index = repairTypeIsExist(txtRepairType.getText());
            String textRepairType = txtRepairType.getText();

            if (buttonType.get().equals(ButtonType.YES)) {
                if (addRepairServicesAndPartsBO.ifRepairTypeExists(textRepairType)) {
                    if (selectedIndex != -1) {
                        tblRepairTypeView.getItems().remove(selectedIndexItemPart);
                        if (addRepairServicesAndPartsBO.deleteRepairServiceType(textRepairType)) {
                            CommonFunctions.setNotificationSuccess("Repair Type Deleted", "Deleting Repair Type is Successful");
                            tblRepairTypeView.refresh();
                            clearRepairTypeFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Deleting Repair Type is Unsuccessful");
                        }
                    } else {
                        tblRepairTypeView.getItems().remove(index);
                        if (addRepairServicesAndPartsBO.deleteRepairServiceType(textRepairType)) {
                            CommonFunctions.setNotificationSuccess("Repair Type Deleted", "Deleting Repair Type is Successful");
                            tblRepairTypeView.refresh();
                            clearRepairTypeFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Deleting Repair Type is Unsuccessful");
                        }
                    }
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearRepairTypeFields() {
        txtRepairType.clear();
        txtRepairCost.clear();
    }

    public int repairTypeIsExist(String repairType) {
        ObservableList<RepairTypeTm> repairTypeObservableList = tblRepairTypeView.getItems();
        for (int i = 0; i < repairTypeObservableList.size(); i++) {
            if (repairType.equals(repairTypeObservableList.get(i).getRepairType())) {
                return i;
            }
        }
        return -1;
    }

    public FXMLLoader getFXMLLoader() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/RepairItemsForm.fxml"));
        return loader;
    }

    public void setDataToRepairTypeComboBox() throws SQLException {
        FXMLLoader loader = getFXMLLoader();
        RepairItemsFormController controller = loader.<RepairItemsFormController>getController();
        controller.setDataToRepairTypeComboBox();
    }

    //----------------------------------------------------------------------------------------

    public void selectRepairPartRowFromTheTable() {
        ObservableList<RepairServicesPartsTm> itemParts = tblItemPartView.getItems();
        tblItemPartView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndexItemPart = (int) newValue;
            txtItemPartCode.setText(itemParts.get(selectedIndexItemPart).getItemCode());
            txtItemPartDescription.setText(itemParts.get(selectedIndexItemPart).getItemDescription());
            txtItemPartQty.setText(String.valueOf(itemParts.get(selectedIndexItemPart).getItemQty()));
            txtItemPartUnitPrice.setText(String.valueOf(itemParts.get(selectedIndexItemPart).getItemUnitPrice()));
        });
    }

    public void getItemPartsEvent(KeyEvent keyEvent) {
        try {
            if (txtItemPartCode.getLength() == 5) {
                ItemDTO itemDTO = addRepairServicesAndPartsBO.searchItems(txtItemPartCode.getText());
                if (txtItemPartCode.getText().equals(itemDTO.getItemCode())) {
                    txtItemPartDescription.setText(itemDTO.getItemDescription());
                    txtItemPartQty.setText(String.valueOf(itemDTO.getItemQty()));
                    txtItemPartUnitPrice.setText(String.valueOf(itemDTO.getItemUnitPrice()));
                    txtItemPartCode.setStyle("-fx-border-color: green");
                } else {
                    txtItemPartCode.setStyle("-fx-border-color: red");
                }
            } else {
                txtItemPartCode.setStyle("-fx-border-color: red");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddItemPartToCombo(ActionEvent actionEvent) {
        RepairServicesPartsDTO dto = new RepairServicesPartsDTO(txtItemPartCode.getText(), txtItemPartDescription.getText(), Integer.parseInt(txtItemPartQty.getText()), Double.parseDouble(txtItemPartUnitPrice.getText()));
        try {
            if (addRepairServicesAndPartsBO.ifItemExists(txtItemPartCode.getText())) {
                if (!addRepairServicesAndPartsBO.ifRepairServicePartExists(txtItemPartCode.getText())) {
                    if (addRepairServicesAndPartsBO.saveRepairServicesParts(dto)) {
                        CommonFunctions.setNotificationSuccess("Item Part Added", "Adding Item Part is Successful");
                        tblItemPartView.getItems().add(new RepairServicesPartsTm(dto.getItemCode(), dto.getItemDescription(), dto.getItemQty(), dto.getItemUnitPrice()));
                        setDataToRepairServicesPartsComboBox();
                        clearRepairPartFields();
                    } else {
                        CommonFunctions.setNotificationSuccess("Try Again", "Adding Item Part is Unsuccessful");
                    }
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Item Part doesn't exists in the Repair Item Parts Stock");
                }
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Item Part doesn't exists in the Item Stock");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToRepairServicesPartsComboBox() throws SQLException {
        FXMLLoader loader = getFXMLLoader();
        RepairItemsFormController controller = loader.<RepairItemsFormController>getController();
        controller.setDataToRepairPartCombo();
    }

    public void setItemPartsTable() throws SQLException {
        tblItemPartView.getItems().clear();
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        tblItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        ArrayList<RepairServicesPartsDTO> allRepairServicesParts = addRepairServicesAndPartsBO.getAllRepairServicesParts();
        for (RepairServicesPartsDTO dto : allRepairServicesParts
        ) {
            tblItemPartView.getItems().add(new RepairServicesPartsTm(dto.getItemCode(), dto.getItemDescription(), dto.getItemQty(), dto.getItemUnitPrice()));
        }
    }

    public void btnDeleteItemPartFromCombo(ActionEvent actionEvent) {
        try {
            int index = repairPartIsExist(txtItemPartCode.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove repair Item Part", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            String repairPart = txtItemPartCode.getText();

            if (buttonType.get().equals(ButtonType.YES)) {
                if (addRepairServicesAndPartsBO.ifRepairServicePartExists(repairPart)) {
                    if (selectedIndexItemPart != -1) {
                        tblItemPartView.getItems().remove(selectedIndexItemPart);
                        if (addRepairServicesAndPartsBO.deleteRepairServicesParts(repairPart)) {
                            CommonFunctions.setNotificationSuccess("Item Part Deleted", "Deleting Item Part from the Combo Box");
                            clearRepairPartFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Deleting Item Part is Unsuccessful");
                        }
                    } else {
                        tblItemPartView.getItems().remove(index);
                        if (addRepairServicesAndPartsBO.deleteRepairServicesParts(repairPart)) {
                            CommonFunctions.setNotificationSuccess("Item Part Deleted", "Deleting Item Part from the Combo Box");
                            clearRepairPartFields();
                        } else {
                            CommonFunctions.setNotificationWarning("Try Again", "Deleting Item Part is Unsuccessful");
                        }
                    }
                } else if (buttonType.get().equals(ButtonType.NO)) {
                    new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int repairPartIsExist(String itemPartCode) {
        ObservableList<RepairServicesPartsTm> itemParts = tblItemPartView.getItems();
        for (int i = 0; i < itemParts.size(); i++) {
            if (itemPartCode.equals(itemParts.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void clearRepairPartFields() {
        txtItemPartCode.clear();
        txtItemPartDescription.clear();
        txtItemPartQty.clear();
        txtItemPartUnitPrice.clear();
    }

}
