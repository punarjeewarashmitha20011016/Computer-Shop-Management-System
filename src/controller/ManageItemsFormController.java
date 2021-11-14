package controller;

import bo.BOFactory;
import bo.custom.ManageItemsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemBrandDTO;
import dto.ItemCategoryDTO;
import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.ItemTm;
import util.CommonFunctions;
import util.Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.CommonFunctions.setNotificationSuccess;
import static util.CommonFunctions.setNotificationWarning;
import static util.Validator.textFields;
import static util.Validator.txtField;

public class ManageItemsFormController {
    public AnchorPane manageItemsPane;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemRam;
    public JFXTextField txtItemStorage;
    public JFXTextField txtItemQuantity;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtAddItemBrand;
    public JFXTextField txtAddItemCategory;
    public TableView<ItemTm> tblItemView;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblItemBrand;
    public TableColumn tblItemCategory;
    public TableColumn tblItemRam;
    public TableColumn tblItemStorage;
    public TableColumn tblItemUnitPrice;
    public TextField txtSearchItemFromTheTable;
    public JFXComboBox<String> cmbItemBrand;
    public JFXComboBox<String> cmbItemCategory;
    public TableColumn tblItemQty;
    public JFXButton addItemCategoryId;
    public JFXButton AddItemBrandId;
    public JFXButton addItemId;
    public TableColumn tblItemBuyingPrice;
    public JFXTextField txtItemBuyingPrice;
    private int selectedRowIndex = -1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapBrand = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> mapCategory = new LinkedHashMap<>();

    Pattern itemCodePattern = Pattern.compile("^(I-)[0-9]{3}$");
    Pattern itemDescriptionPattern = Pattern.compile("^[A-z ]+[0-9]*$");
    Pattern itemBrandPattern = Pattern.compile("^[A-z ]+$");
    Pattern itemCategoryPattern = Pattern.compile("^[A-z ]+$");
    Pattern itemRamPattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");
    Pattern itemStoragePattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");
    Pattern itemQtyPattern = Pattern.compile("^[0-9]+$");
    Pattern itemBuyingPricePattern = Pattern.compile("^[0-9]+[.]?[0]*$");
    Pattern itemUnitPricePattern = Pattern.compile("^[0-9]+[.]?[0]*$");

    private ManageItemsBO manageItemsBO = (ManageItemsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.ITEM);
    private final ObservableList<ItemTm>itemObservableList=FXCollections.observableArrayList();

    public void initialize() {
        textFields.clear();
        validateFields();
        new Validator(map);

        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        tblItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        tblItemRam.setCellValueFactory(new PropertyValueFactory<>("itemRam"));
        tblItemStorage.setCellValueFactory(new PropertyValueFactory<>("itemStorage"));
        tblItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        tblItemBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("itemBuyingPrice"));
        tblItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        try {
            getItemCode();
            setItemsToTable();
            setItemCategoryToComboBox();
            setItemBrandsToComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblItemView.refresh();

        searchItemsFromTheTable();

        selectRowFromTheTable();

    }

    public void selectRowFromTheTable() {
        tblItemView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            CommonFunctions.setEnableTextFields(map);
            selectedRowIndex = (int) newValue;
            txtItemCode.setText(itemObservableList.get((Integer) newValue).getItemCode());
            txtItemDescription.setText(itemObservableList.get((Integer) newValue).getItemDescription());
            cmbItemBrand.getSelectionModel().select(itemObservableList.get((Integer) newValue).getItemBrand());
            cmbItemCategory.getSelectionModel().select(itemObservableList.get((Integer) newValue).getItemCategory());
            txtItemRam.setText(String.valueOf(itemObservableList.get((Integer) newValue).getItemRam()));
            txtItemStorage.setText(String.valueOf(itemObservableList.get((Integer) newValue).getItemStorage()));
            txtItemQuantity.setText(String.valueOf(itemObservableList.get((Integer) newValue).getItemQty()));
            txtItemBuyingPrice.setText(String.valueOf(itemObservableList.get((Integer) newValue).getItemBuyingPrice()));
            txtItemUnitPrice.setText(String.valueOf(itemObservableList.get((Integer) newValue).getItemUnitPrice()));
        });
    }

    public void getAllFromItemCode(ActionEvent actionEvent) {
        try {
            ItemDTO items = manageItemsBO.searchItem(txtItemCode.getText());
            txtItemDescription.setText(items.getItemDescription());
            cmbItemBrand.getSelectionModel().select(items.getItemBrand());
            cmbItemCategory.getSelectionModel().select(items.getItemCategory());
            txtItemRam.setText(String.valueOf(items.getItemRam()));
            txtItemStorage.setText(String.valueOf(items.getItemStorage()));
            txtItemQuantity.setText(String.valueOf(items.getItemQty()));
            txtItemBuyingPrice.setText(String.valueOf(items.getItemBuyingPrice()));
            txtItemUnitPrice.setText(String.valueOf(items.getItemUnitPrice()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddItem(ActionEvent actionEvent) {
        String itemBrand = cmbItemBrand.getValue().toString();
        String itemCategory = cmbItemCategory.getValue().toString();
        ItemDTO items = new ItemDTO(txtItemCode.getText(), txtItemDescription.getText(), itemBrand, itemCategory, Double.parseDouble(txtItemRam.getText()), Double.parseDouble(txtItemStorage.getText()), Integer.parseInt(txtItemQuantity.getText()), Double.parseDouble(txtItemBuyingPrice.getText()), Double.parseDouble(txtItemUnitPrice.getText()));
        try {
            if (manageItemsBO.ifItemExists(txtItemCode.getText())) {
                CommonFunctions.setNotificationWarning("Item Code exists", "Please Try Again ItemCode exists");
            } else {
                if (manageItemsBO.saveItem(items)) {
                    itemObservableList.add(new ItemTm(items.getItemCode(), items.getItemDescription(), items.getItemBrand(), items.getItemCategory(), items.getItemRam(), items.getItemStorage(), items.getItemQty(), items.getItemBuyingPrice(), items.getItemUnitPrice()));
                    CommonFunctions.clearFields(map);
                    CommonFunctions.setDisableFields(map);
                    getItemCode();
                    cmbItemCategory.getSelectionModel().clearSelection();
                    cmbItemBrand.getSelectionModel().clearSelection();
                    setNotificationSuccess("Item Added", "Item Added Successfully");
                } else {
                    setNotificationWarning("Try Again", "Adding Item is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnUpdateItem(ActionEvent actionEvent) {
        String itemBrand = cmbItemBrand.getValue().toString();
        String itemCategory = cmbItemCategory.getValue().toString();
        ItemDTO items = new ItemDTO(txtItemCode.getText(), txtItemDescription.getText(), itemBrand, itemCategory, Double.parseDouble(txtItemRam.getText()), Double.parseDouble(txtItemStorage.getText()), Integer.parseInt(txtItemQuantity.getText()), Double.parseDouble(txtItemBuyingPrice.getText()), Double.parseDouble(txtItemUnitPrice.getText()));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update the details of the Item", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        try {
            if (buttonType.get().equals(ButtonType.YES)) {
                if (manageItemsBO.ifItemExists(txtItemCode.getText())) {
                    if (selectedRowIndex != -1) {
                        itemObservableList.remove(selectedRowIndex);
                        itemObservableList.add(new ItemTm(items.getItemCode(), items.getItemDescription(), items.getItemBrand(), items.getItemCategory(), items.getItemRam(), items.getItemStorage(), items.getItemQty(), items.getItemBuyingPrice(), items.getItemUnitPrice()));
                    } else {
                        if (manageItemsBO.updateItem(items)) {
                            int index = getIndexOfExistsItem(txtItemCode.getText());
                            itemObservableList.remove(index);
                            itemObservableList.add(new ItemTm(items.getItemCode(), items.getItemDescription(), items.getItemBrand(), items.getItemCategory(), items.getItemRam(), items.getItemStorage(), items.getItemQty(), items.getItemBuyingPrice(), items.getItemUnitPrice()));
                            CommonFunctions.clearFields(map);
                            CommonFunctions.setDisableFields(map);
                            getItemCode();
                            cmbItemCategory.getSelectionModel().clearSelection();
                            cmbItemBrand.getSelectionModel().clearSelection();
                            setNotificationSuccess("Updated Item", "Updating Item is Successful");
                        } else {
                            setNotificationWarning("Try Again", "Updating Item is Unsuccessful");
                        }
                    }
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "ItemCode doesn't exists");
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnDeleteItem(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove Item", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        String itemCode = txtItemCode.getText();
        try {
            if (buttonType.get().equals(ButtonType.YES)) {
                if (selectedRowIndex != -1) {
                    itemObservableList.remove(selectedRowIndex);
                    if (manageItemsBO.deleteItem(itemCode)) {
                        setNotificationSuccess("Item Deleted", "Deleting Item is Successful");
                        CommonFunctions.clearFields(map);
                        CommonFunctions.setDisableFields(map);
                        cmbItemCategory.getSelectionModel().clearSelection();
                        cmbItemBrand.getSelectionModel().clearSelection();
                        getItemCode();
                    }
                } else {
                    int index = getIndexOfExistsItem(itemCode);
                    itemObservableList.remove(index);
                    if (manageItemsBO.deleteItem(itemCode)) {
                        setNotificationSuccess("Item Deleted", "Deleting Item is Successful");
                        CommonFunctions.clearFields(map);
                        CommonFunctions.setDisableFields(map);
                        cmbItemCategory.getSelectionModel().clearSelection();
                        cmbItemBrand.getSelectionModel().clearSelection();
                        getItemCode();
                    }
                    clearTextFields();
                    getItemCode();
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                setNotificationWarning("Try Again", "Deleting Item is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getIndexOfExistsItem(String itemCode) {
        int count = 0;
        for (ItemTm i : itemObservableList
        ) {
            if (i.getItemCode().equals(itemCode)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    public void btnAddItemBrand(ActionEvent actionEvent) {
        try {
            if (txtAddItemBrand.getText().equals(null)) {
                setNotificationWarning("Try Again", "Adding Item Brand is unsuccessful");
            } else {
                ItemBrandDTO itemBrandDTO = new ItemBrandDTO(txtAddItemBrand.getText());
                if (manageItemsBO.saveItemBrand(itemBrandDTO)) {
                    setNotificationSuccess("Item Brand Added", "Item Brand Adding is Successful");
                    cmbItemBrand.getItems().add(itemBrandDTO.getItemBrand());
                    clearTextFields();
                    getItemCode();

                } else {
                    setNotificationWarning("Try Again", "Item Brand adding is Unsuccessful");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setItemBrandsToComboBox() throws SQLException {
        cmbItemBrand.getItems().clear();
        ArrayList<ItemBrandDTO> itemBrands = manageItemsBO.getItemBrands();
        ObservableList<String> itemBrandDTOS = FXCollections.observableArrayList();
        for (ItemBrandDTO i : itemBrands
        ) {
            itemBrandDTOS.add(i.getItemBrand());
        }
        cmbItemBrand.setItems(itemBrandDTOS);
    }

    public void btnAddItemCategory(ActionEvent actionEvent) {
        try {
            if (txtAddItemCategory.getText().equals(null)) {
                setNotificationWarning("Try Again", "Adding Item Category is unsuccessful");
            } else {
                ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(txtAddItemCategory.getText());
                if (manageItemsBO.saveItemCategory(itemCategoryDTO)) {
                    setNotificationSuccess("Item Category Added", "Item Category Adding is Successful");
                    cmbItemCategory.getItems().add(itemCategoryDTO.getItemCategory());
                    clearTextFields();
                    getItemCode();
                } else {
                    setNotificationWarning("Try Again", "Item Category adding is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setItemCategoryToComboBox() throws SQLException {
        cmbItemCategory.getItems().clear();
        ArrayList<ItemCategoryDTO> itemCategories = manageItemsBO.getItemCategories();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (ItemCategoryDTO i : itemCategories
        ) {
            list.add(i.getItemCategory());
        }
        cmbItemCategory.setItems(list);
    }

    public void getItemCode() throws SQLException {
        txtItemCode.setDisable(false);
        String itemCode = manageItemsBO.getItemCode();
        txtItemCode.setText(itemCode);
    }

    public void setItemsToTable() throws SQLException {
        itemObservableList.clear();
        ArrayList<ItemDTO> all = manageItemsBO.getAll();
        for (ItemDTO i : all
        ) {
            itemObservableList.add(new ItemTm(i.getItemCode(), i.getItemDescription(), i.getItemBrand(), i.getItemCategory(), i.getItemRam(), i.getItemStorage(), i.getItemQty(), i.getItemBuyingPrice(), i.getItemUnitPrice()));
        }
        tblItemView.setItems(itemObservableList);
    }

    public void searchItemsFromTheTable() {
        FilteredList<ItemTm> filteredData = new FilteredList<>(itemObservableList, p -> true);

        txtSearchItemFromTheTable.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getItemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ItemTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblItemView.comparatorProperty());
        tblItemView.setItems(sortedData);
    }

    private void clearTextFields() {
        txtAddItemBrand.clear();
        txtAddItemCategory.clear();
    }

    public void validateFields() {
        map.put(txtItemCode, itemCodePattern);
        map.put(txtItemDescription, itemDescriptionPattern);
        map.put(txtItemRam, itemRamPattern);
        map.put(txtItemStorage, itemStoragePattern);
        map.put(txtItemQuantity, itemQtyPattern);
        map.put(txtItemBuyingPrice, itemBuyingPricePattern);
        map.put(txtItemUnitPrice, itemUnitPricePattern);
        mapBrand.put(txtAddItemBrand, itemBrandPattern);
        mapCategory.put(txtAddItemCategory, itemCategoryPattern);
    }

    public void validateItemBrandEvent(KeyEvent keyEvent) {
        if (itemBrandPattern.matcher(txtAddItemBrand.getText()).matches()) {
            txtAddItemBrand.setStyle("-fx-border-color: green");
            AddItemBrandId.setDisable(false);
        } else {
            AddItemBrandId.setDisable(true);
            txtAddItemBrand.setStyle("-fx-border-color: red");
        }

    }

    public void validateItemCategoryEvent(KeyEvent keyEvent) {
        if (itemCategoryPattern.matcher(txtAddItemCategory.getText()).matches()) {
            txtAddItemCategory.setStyle("-fx-border-color: green");
            addItemCategoryId.setDisable(false);
        } else {
            addItemCategoryId.setDisable(true);
            txtAddItemCategory.setStyle("-fx-border-color: red");
        }
    }

    public void validateFieldsWithoutBrandAndCategory(KeyEvent keyEvent) {
        Object normalResponse = Validator.validate(map, addItemId);
        if (normalResponse instanceof Boolean) {
            addItemId.setDisable(false);
            txtField.setDisable(false);
        }
        if (keyEvent.getCode() == KeyCode.TAB) {
            if (normalResponse instanceof TextField) {
                TextField txt = (TextField) normalResponse;
                addItemId.setDisable(true);
                txt.requestFocus();
            }
        }
    }
}
