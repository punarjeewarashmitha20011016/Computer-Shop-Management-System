package controller;

import bo.BOFactory;
import bo.custom.ReturnsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.NormalOrderDTO;
import dto.NormalOrderDetailsDTO;
import dto.ReturnsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;
import util.Validator;
import view.tm.OrderDetailsTm;
import view.tm.ReturnItemsTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.Validator.txtField;

public class ReturnsFormController {
    public AnchorPane returnItemsPane;
    public TextField txtSearchOrderDetails;
    public JFXTextField txtCustomerId;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderTime;
    public JFXTextField txtOrderPrice;
    public TextField txtSearchReturnItems;
    public JFXTextField txtReturnId;
    public JFXTextField txtReturnOrderId;
    public JFXTextField txtReturnCustomerId;
    public JFXTextField txtReturnItemCode;
    public JFXTextField txtReturnItemDescription;
    public JFXTextField txtReturnQty;
    public JFXTextField txtReturnReason;
    public JFXTextField txtReturnItemPrice;
    public TableView<OrderDetailsTm> tblOrderDetailsView;
    public TableColumn tblOrderId;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblQty;
    public TableColumn tblDiscount;
    public TableColumn tblPrice;
    public TableColumn tblReturnId;
    public TableColumn tblReturnOrderId;
    public TableColumn tblReturnItemCode;
    public TableColumn tblReturnItemDescription;
    public TableColumn tblReturnQty;
    public TableColumn tblReturnReason;
    public TableColumn tblReturnPrice;
    public TableView<ReturnItemsTm> tblReturnItemsView;
    public JFXButton acceptReturnBtnId;
    public JFXButton clearBtnId;

    private ReturnsBO returnsBO = (ReturnsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.RETURNS);
    private final ObservableList<ReturnItemsTm>returnItemsTms= FXCollections.observableArrayList();

    String returnItemCode;
    String returnItemDescription;
    double returnItemPrice;
    String orderId;

    public ArrayList<String> normalOrderIdList = new ArrayList<>();
    public ArrayList<String> returnIdList = new ArrayList<>();
    public ArrayList<String> itemCodesList = new ArrayList<>();
    public AutoCompletionBinding<String> autoCompletionBinding;
    //---------------------------------------------------------------------------------------------
    LinkedHashMap<TextField, Pattern> normalOrderMap = new LinkedHashMap<>();
    Pattern customerIdPattern = Pattern.compile("^(C-)?([0-9]{3})?$");
    Pattern normalOrderDatePattern = Pattern.compile("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$");
    Pattern normalOrderTimePattern = Pattern.compile("^[0-9]{2}[:][0-9]{2}[:][0-9]{2}[\\s]?(PM|AM)$");
    Pattern normalOrderCostPattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");
    //---------------------------------------------------------------------------------------------
    LinkedHashMap<TextField, Pattern> returnMap = new LinkedHashMap<>();
    Pattern returnIdPattern = Pattern.compile("^(RE-)[0-9]{3}$");
    Pattern returnOrderIdPattern = Pattern.compile("^(O-)[0-9]{3}$");
    Pattern returnCustomerPattern = Pattern.compile("^(C-)([0-9]{3})?$");
    Pattern returnItemCodePattern = Pattern.compile("^(I-)[0-9]{3}$");
    Pattern returnItemDescriptionPattern = Pattern.compile("^[A-z ]+[0-9]*$");
    Pattern returnQtyPattern = Pattern.compile("^[0-9]+$");
    Pattern returnReasonPattern = Pattern.compile("^[A-z ]+$");
    Pattern returnCostPattern = Pattern.compile("^[0-9]+[.]?[0-9]+$");

    public void initialize() {
        validateNormalOrderFields();
        Validator validator1 = new Validator();
        validator1.addDataToTextFieldList(normalOrderMap);

        validateReturnFields();
        Validator validator2 = new Validator();
        validator2.addDataToTextFieldList(returnMap);

        setDisableItemCodeWhenCustomerIdNull();

        try {
            tblOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            tblQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            tblDiscount.setCellValueFactory(new PropertyValueFactory<>("itemDiscount"));
            tblPrice.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
            tblOrderDetailsView.refresh();

            getReturnId();

            setDataToReturnIdList();
            setDataToNormalOrderIdList();
            setDataItemCodesList();
            tblReturnId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
            tblReturnOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            tblReturnItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            tblReturnItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            tblReturnQty.setCellValueFactory(new PropertyValueFactory<>("returnQty"));
            tblReturnReason.setCellValueFactory(new PropertyValueFactory<>("returnReason"));
            tblReturnPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            tblReturnItemsView.refresh();
            setDataToReturnItemsTable();
            searchFromTheReturnTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void validateNormalOrderFields() {
        normalOrderMap.put(txtCustomerId, customerIdPattern);
        normalOrderMap.put(txtOrderDate, normalOrderDatePattern);
        normalOrderMap.put(txtOrderTime, normalOrderTimePattern);
        normalOrderMap.put(txtOrderPrice, normalOrderCostPattern);
    }

    public void validateReturnFields() {
        returnMap.put(txtReturnId, returnIdPattern);
        returnMap.put(txtReturnOrderId, returnOrderIdPattern);
        if (txtReturnCustomerId.getLength() != 0) {
            returnMap.put(txtReturnCustomerId, returnCustomerPattern);
        }
        returnMap.put(txtReturnItemCode, returnItemCodePattern);
        returnMap.put(txtReturnItemDescription, returnItemDescriptionPattern);
        returnMap.put(txtReturnQty, returnQtyPattern);
        returnMap.put(txtReturnReason, returnReasonPattern);
        returnMap.put(txtReturnItemPrice, returnCostPattern);
    }


    private void setDataToNormalOrderTable() throws SQLException {
        tblOrderDetailsView.getItems().clear();
        ArrayList<NormalOrderDetailsDTO> allNormalOrderDetails = returnsBO.getAllNormalOrderDetails(txtSearchOrderDetails.getText());
        for (NormalOrderDetailsDTO dto : allNormalOrderDetails
        ) {
            tblOrderDetailsView.getItems().add(new OrderDetailsTm(dto.getOrderId(), dto.getItemCode(), dto.getItemDescription(), dto.getItemBrand(), dto.getItemCategory(), dto.getItemRam(), dto.getItemStorage(), dto.getQtyOnHand(), dto.getItemDiscount(), dto.getItemCost()));
        }
    }

    private void setDataToReturnIdList() throws SQLException {
        returnIdList = returnsBO.getReturnIds();
    }

    public void btnAcceptReturn(ActionEvent actionEvent) {
        String returnId = txtReturnId.getText();
        String orderId = txtReturnOrderId.getText();
        String customerId = txtReturnCustomerId.getText();
        int returnQty = Integer.parseInt(txtReturnQty.getText());
        String returnReason = txtReturnReason.getText();

        ReturnsDTO returnItemsDto = new ReturnsDTO(returnId, orderId, customerId, returnItemCode, returnItemDescription, returnQty, returnReason, returnItemPrice);
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this item to the return item section", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (returnsBO.saveReturns(returnItemsDto)) {
                    CommonFunctions.setNotificationSuccess("Return Saved", "Returned Item Saved Successfully");
                    returnItemsTms.add(new ReturnItemsTm(returnId, orderId, customerId, returnItemCode, returnItemDescription, returnQty, returnReason, returnItemPrice));
                    if (returnsBO.updateIncome(returnItemsDto.getItemPrice())) {
                        CommonFunctions.setNotificationSuccess("Updated Income", "Deducted Returned Cost from the Income");
                        clearReturnFields();
                        getReturnId();
                        clearNormalOrderFields();
                        txtSearchOrderDetails.clear();
                        setDisableFields();
                        txtReturnId.setDisable(false);
                    } else {
                        CommonFunctions.setNotificationWarning("Try Again", "Update Income is Unsuccessful");
                        return;
                    }
                } else {
                    CommonFunctions.setNotificationSuccess("Try Again", "Saving the returned Item is unsuccessful");
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                clearReturnFields();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDisableFields() {
        CommonFunctions.setDisableFields(returnMap);
    }

    public void btnClear(ActionEvent actionEvent) {
        clearReturnFields();
    }

    public void clearReturnFields() {
        txtReturnId.clear();
        txtReturnOrderId.clear();
        txtReturnCustomerId.clear();
        txtReturnItemCode.clear();
        txtReturnItemDescription.clear();
        txtReturnQty.clear();
        txtReturnReason.clear();
        txtReturnItemPrice.clear();
        txtSearchReturnItems.clear();
    }

    public void clearNormalOrderFields() {
        txtOrderPrice.clear();
        txtCustomerId.clear();
        txtOrderTime.clear();
        txtOrderDate.clear();
        txtReturnOrderId.clear();
        txtReturnCustomerId.clear();
    }

    public void getReturnId() throws SQLException {
        String returnId = returnsBO.generateReturnId();
        txtReturnId.setText(returnId);
    }

    public void setDataToNormalOrderIdList() throws SQLException {
        normalOrderIdList = returnsBO.getNormalOrderIds();
    }

    public void searchOrderDetailsEvent(KeyEvent keyEvent) {
        txtCustomerId.setDisable(false);
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchOrderDetails, normalOrderIdList);
        switch (keyEvent.getCode()) {
            case SPACE:
                CommonFunctions.autoCompletionLearnWord(normalOrderIdList, txtSearchOrderDetails.getText().trim(), autoCompletionBinding, txtSearchOrderDetails);
                break;
            case BACK_SPACE:
                tblOrderDetailsView.getItems().clear();
                clearNormalOrderFields();
                break;
            default:
                break;
        }

        String orderId = txtSearchOrderDetails.getText();
        try {
            if (orderId.length() == 5) {
                NormalOrderDTO order = returnsBO.searchNormalOrders(orderId);
                txtCustomerId.setText(order.getCustomerId());
                txtOrderDate.setText(order.getOrderDate());
                txtOrderTime.setText(order.getOrderTime());
                txtOrderPrice.setText(String.valueOf(order.getOrderCost()));
                txtReturnCustomerId.setText(order.getCustomerId());
                txtReturnOrderId.setText(order.getOrderId());
                setDataToNormalOrderTable();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void returnItemQtyEvent(KeyEvent event) {
        if (txtReturnQty.getLength() > 0) {
            int returnQty = Integer.parseInt(txtReturnQty.getText());
            double totalBilledItemCost = 0.0;
            double discountedPriceTotTheItem = 0.0;
            double itemUnitPrice = 0.0;
            int calculateReturnedItemsPriceDiscountInPercentage = 0;
            double priceOfReturnItems = 0.0;
            try {
                ItemDTO item = returnsBO.searchItem(txtReturnItemCode.getText());
                itemUnitPrice = item.getItemUnitPrice();

                ObservableList<OrderDetailsTm> orderDetails = tblOrderDetailsView.getItems();
                for (OrderDetailsTm ord : orderDetails
                ) {
                    if (ord.getItemCode().equals(returnItemCode)) {
                        totalBilledItemCost = ord.getItemCost();
                        System.out.println(totalBilledItemCost);
                        if (ord.getItemDiscount() != 0) {
                            discountedPriceTotTheItem = ord.getItemDiscount();
                            System.out.println(discountedPriceTotTheItem);
                            break;
                        } else {
                            discountedPriceTotTheItem = 0.0;
                            break;
                        }
                    }
                }
                calculateReturnedItemsPriceDiscountInPercentage = (int) ((100 / totalBilledItemCost) * discountedPriceTotTheItem);
                System.out.println(calculateReturnedItemsPriceDiscountInPercentage);
                priceOfReturnItems = returnQty * itemUnitPrice;
                System.out.println(priceOfReturnItems);
                double discountOfTheReturnQty = (calculateReturnedItemsPriceDiscountInPercentage * priceOfReturnItems) / 100;
                System.out.println(discountOfTheReturnQty);
                returnItemPrice = itemUnitPrice - discountOfTheReturnQty;
                System.out.println(returnItemPrice);
                txtReturnItemPrice.setText(String.valueOf(returnItemPrice));
                //txtReturnReason.setDisable(false);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setDataItemCodesList() throws SQLException {
        itemCodesList = returnsBO.getItemCodes();
    }

    public void returnItemCodeEvent(KeyEvent event) {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtReturnItemCode, itemCodesList);
        switch (event.getCode()) {
            case SPACE:
                CommonFunctions.autoCompletionLearnWord(itemCodesList, txtSearchReturnItems.getText().trim(), autoCompletionBinding, txtSearchReturnItems);
                break;
            case BACK_SPACE:
                txtReturnItemDescription.clear();
                break;
            default:
                break;
        }

        String itemCode = txtReturnItemCode.getText();
        if (itemCode.length() == 5) {
            try {
                ItemDTO items = returnsBO.searchItem(txtReturnItemCode.getText());
                returnItemCode = items.getItemCode();
                returnItemDescription = items.getItemDescription();
                txtReturnItemDescription.setText(returnItemDescription);
                System.out.println(returnItemDescription);
                txtReturnItemDescription.setDisable(false);
                orderId = txtReturnOrderId.getText();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setDataToReturnItemsTable() throws SQLException {
        returnItemsTms.clear();
        ArrayList<ReturnsDTO> allReturns = returnsBO.getAllReturns();
        for (ReturnsDTO dto : allReturns
        ) {
            returnItemsTms.add(new ReturnItemsTm(dto.getReturnId(), dto.getOrderId(), dto.getCustomerId(), dto.getItemCode(), dto.getItemDescription(), dto.getReturnQty(), dto.getReturnReason(), dto.getItemPrice()));
        }tblReturnItemsView.setItems(returnItemsTms);
    }

    public void searchFromTheReturnTable() {
        ObservableList<ReturnItemsTm> returnsObservableList = tblReturnItemsView.getItems();
        FilteredList<ReturnItemsTm> filteredData = new FilteredList<>(returnsObservableList, p -> true);

        txtSearchReturnItems.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getReturnId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getOrderId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ReturnItemsTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblReturnItemsView.comparatorProperty());
        tblReturnItemsView.setItems(sortedData);
    }

    public void normalOrderKeyEvent(KeyEvent keyEvent) {
        JFXButton btn = new JFXButton();
        Object response = Validator.validate(normalOrderMap, btn);

        if (response instanceof Boolean) {
            txtField.setDisable(false);
        }
        if (keyEvent.getCode().equals(KeyCode.TAB)) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
    }

    public void returnKeyEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(returnMap, acceptReturnBtnId);
        if (response instanceof Boolean) {
            txtField.setDisable(false);
            acceptReturnBtnId.setDisable(false);
            clearBtnId.setDisable(false);
        }
        if (keyEvent.getCode().equals(KeyCode.TAB)) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
                clearBtnId.setDisable(true);
            }
        }
        returnItemCodeEvent(keyEvent);
        returnItemQtyEvent(keyEvent);
    }

    public void setDisableItemCodeWhenCustomerIdNull() {
        txtReturnCustomerId.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (txtReturnCustomerId.getLength() == 0) {
                    System.out.println("IsEmpty");
                    switch (event.getCode()) {
                        case ENTER:
                            txtReturnItemCode.setDisable(false);
                            break;
                    }
                }
            }
        });
    }
}
