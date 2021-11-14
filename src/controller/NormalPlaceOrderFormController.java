package controller;

import bo.BOFactory;
import bo.custom.NormalPlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CommonFunctions;
import util.Validator;
import view.tm.AddToCartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static controller.LoginFormController.cashierName;
import static util.CommonFunctions.setDate;
import static util.CommonFunctions.setTime;
import static util.Validator.txtField;

public class NormalPlaceOrderFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContactNo;
    public JFXTextField txtCustomerAddress;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemBrand;
    public JFXTextField txtItemQty;
    public JFXTextField txtItemQtyOnHand;
    public JFXTextField txtItemUnitPrice;
    public JFXTextField txtAddDiscount;
    public TableView<AddToCartTm> tblAddToCartView;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblItemBrand;
    public TableColumn tblItemCategory;
    public TableColumn tblItemQty;
    public TableColumn tblItemQtyOnHand;
    public TableColumn tblDiscountPrice;
    public TableColumn tblTotal;
    public Label lblDiscount;
    public Label lblTotal;
    public Label lblSubTotal;
    public Label lblSetOrderId;
    public AnchorPane normalOrdersPane;
    public JFXButton addToCartBtnId;
    public JFXButton clearBtnId;
    public JFXButton placeOrderBtnId;
    public JFXButton discardBtnId;
    public JFXTextField txtItemCategory;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]+$");
    Pattern discountPattern = Pattern.compile("^[0-9]+[.]?[0-9]*$");

    int selectedIndex = -1;
    private NormalPlaceOrderBO normalPlaceOrderBO = (NormalPlaceOrderBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.NORMALPLACEORDER);

    public void initialize() {
        validateQtyOnHandAndDiscountFields();
        new Validator(map);
        try {
            setCustomerIdToComboBox();
            setItemCodeToComboBox();
            setOrderId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        tblItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        tblItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblItemQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblDiscountPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("totalItemCost"));
        tblAddToCartView.refresh();

        selectRowFromTheTable();
    }

    public void selectRowFromTheTable() {
        tblAddToCartView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex = (int) newValue;
        });
    }

    public void validateQtyOnHandAndDiscountFields() {
        map.put(txtItemQtyOnHand, qtyOnHandPattern);
        map.put(txtAddDiscount, discountPattern);
    }

    public void validateDiscountAndQtyOnHandFieldsEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(map, addToCartBtnId);
        if (response instanceof Boolean) {
            addToCartBtnId.setDisable(false);
            clearBtnId.setDisable(false);
            txtField.setDisable(false);
        }
        if (response instanceof TextField) {
            if (keyEvent.getCode() == KeyCode.TAB) {
                TextField txt = (TextField) response;
                txt.requestFocus();
                addToCartBtnId.setDisable(true);
                clearBtnId.setDisable(true);
            }
        }
    }

    public void setCustomerIdToComboBox() throws SQLException {
        cmbCustomerId.getItems().clear();
        ArrayList<String> customerIds = normalPlaceOrderBO.getCustomerIds();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (String s : customerIds
        ) {
            ids.add(s);
        }
        cmbCustomerId.setItems(ids);
    }

    public void setItemCodeToComboBox() throws SQLException {
        cmbItemCode.getItems().clear();
        ArrayList<String> itemCodes = normalPlaceOrderBO.getItemCodes();
        ObservableList<String> codes = FXCollections.observableArrayList();
        for (String s : itemCodes
        ) {
            codes.add(s);
        }
        cmbItemCode.setItems(codes);
    }

    public void setCustomerDetailsToTextFields(ActionEvent actionEvent) {
        if (cmbCustomerId.getSelectionModel().getSelectedItem() != null) {
            String customerId = cmbCustomerId.getValue();
            try {
                CustomerDTO c1 = normalPlaceOrderBO.searchCustomers(customerId);
                txtCustomerName.setText(c1.getCustomerName());
                txtCustomerContactNo.setText(String.valueOf(c1.getCustomerContactNo()));
                txtCustomerAddress.setText(c1.getCustomerAddress());
                txtCustomerName.setDisable(false);
                txtCustomerContactNo.setDisable(false);
                txtCustomerAddress.setDisable(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setItemDetailsToTextFields(ActionEvent actionEvent) {
        if (cmbItemCode.getSelectionModel().getSelectedItem() != null) {
            String itemCode = cmbItemCode.getValue();
            try {
                ItemDTO i1 = normalPlaceOrderBO.searchAllItems(itemCode);
                txtItemDescription.setText(i1.getItemDescription());
                txtItemBrand.setText(i1.getItemBrand());
                txtItemCategory.setText(i1.getItemCategory());
                txtItemQty.setText(String.valueOf(i1.getItemQty()));
                txtItemUnitPrice.setText(String.valueOf(i1.getItemUnitPrice()));

                txtItemDescription.setDisable(false);
                txtItemBrand.setDisable(false);
                txtItemCategory.setDisable(false);
                txtItemQty.setDisable(false);
                txtItemUnitPrice.setDisable(false);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setDisableFields() {
        txtCustomerName.setDisable(true);
        txtCustomerContactNo.setDisable(true);
        txtCustomerAddress.setDisable(true);

        txtItemDescription.setDisable(true);
        txtItemBrand.setDisable(true);
        txtItemCategory.setDisable(true);
        txtItemQty.setDisable(true);
        txtItemUnitPrice.setDisable(true);
    }

    public void setOrderId() throws SQLException {
        String id = normalPlaceOrderBO.getNormalOrderId();
        lblSetOrderId.setText(id);
    }

    public void btnAddToCart(ActionEvent actionEvent) {
        clearBtnId.setDisable(false);
        setDisableFields();
        String itemCode = cmbItemCode.getValue();
        String itemDescription = txtItemDescription.getText();
        String itemBrand = txtItemBrand.getText();
        String itemCategory = txtItemCategory.getText();
        int itemQty = Integer.parseInt(txtItemQty.getText());
        int itemQtyOnHand = Integer.parseInt(txtItemQtyOnHand.getText());
        double discount = 0.0;

        if (txtAddDiscount.getLength() == 0) {
            discount = 0.0;
        } else {
            discount = Double.parseDouble(txtAddDiscount.getText());
        }
        double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        double priceOfCalculatedQtyOnHand = unitPrice * itemQtyOnHand;
        double discountForQtyOnHandItem = (priceOfCalculatedQtyOnHand * discount) / 100;
        double totalCost = priceOfCalculatedQtyOnHand - discountForQtyOnHandItem;

        AddToCartTm cart = new AddToCartTm(itemCode, itemDescription, itemBrand, itemCategory, itemQty, itemQtyOnHand, discountForQtyOnHandItem, totalCost);

        int index = isExist(cart);

        if (index == -1) {
            tblAddToCartView.getItems().add(cart);
        } else {
            AddToCartTm temp = new AddToCartTm(cart.getItemCode(), cart.getItemDescription(), cart.getItemBrand(), cart.getItemCategory(), cart.getQty(), cart.getQtyOnHand(), cart.getDiscountedPrice(), cart.getTotalItemCost());
            tblAddToCartView.getItems().remove(index);
            tblAddToCartView.getItems().add(new AddToCartTm(temp.getItemCode(), temp.getItemDescription(), temp.getItemBrand(), temp.getItemCategory(), temp.getQty() - itemQtyOnHand, temp.getQtyOnHand() + itemQtyOnHand, temp.getDiscountedPrice() + discountForQtyOnHandItem, temp.getTotalItemCost() + totalCost));
        }
        double cost = calculateTotalCostOfTheOrder();
        double orderCost = cost - calculateDiscountForEntireOrder();
        lblTotal.setText(String.valueOf(orderCost));

        double subTotal = calculateTotalCostOfTheOrder();
        lblSubTotal.setText(String.valueOf(subTotal));

        lblDiscount.setText(String.valueOf(calculateDiscountForEntireOrder()));

        placeOrderBtnId.setDisable(false);
        discardBtnId.setDisable(false);
        txtAddDiscount.clear();
    }

    public double calculateTotalCostOfTheOrder() {
        ObservableList<AddToCartTm> cartList = tblAddToCartView.getItems();
        double cost = 0.0;
        for (int i = 0; i < cartList.size(); i++) {
            cost += cartList.get(i).getTotalItemCost();
        }
        return cost;
    }

    public double calculateDiscountForEntireOrder() {
        ObservableList<AddToCartTm> cartList = tblAddToCartView.getItems();
        double totalDiscount = 0.0;
        for (int i = 0; i < cartList.size(); i++) {
            totalDiscount += cartList.get(i).getDiscountedPrice();
        }
        return totalDiscount;
    }

    public int isExist(AddToCartTm cart) {
        ObservableList<AddToCartTm> cartList = tblAddToCartView.getItems();
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getItemCode().equals(cart.getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void btnClear(ActionEvent actionEvent) {
        tblAddToCartView.getItems().remove(selectedIndex);
    }

    public void clearItemFields() {
        txtAddDiscount.clear();
        txtItemDescription.clear();
        txtItemBrand.clear();
        txtItemCategory.clear();
        txtItemQty.clear();
        txtItemQtyOnHand.clear();
        txtItemUnitPrice.clear();
    }

    public void clearCustomerItemFields() {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        txtCustomerContactNo.clear();
        txtCustomerAddress.clear();
        lblDiscount.setText("");
        lblSubTotal.setText("");
        lblTotal.setText("");

        clearItemFields();
    }

    public void btnPlaceOrderAction(ActionEvent actionEvent) {
        String orderId = lblSetOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        String date = setDate;
        String time = setTime;
        ArrayList<ItemDetailsDTO> items = new ArrayList<>();
        double totalOrderCost = calculateTotalCostOfTheOrder();

        try {
            for (AddToCartTm cart : tblAddToCartView.getItems()
            ) {
                //------------------------------------------------------------------------
                ItemDTO itemDTO = normalPlaceOrderBO.searchAllItems(cart.getItemCode());
                //----------------------------------------------------------------------------
                items.add(new ItemDetailsDTO(cart.getItemCode(), cart.getItemDescription(), cart.getItemBrand(), cart.getItemCategory(), itemDTO.getItemRam(), itemDTO.getItemStorage(), cart.getQtyOnHand(), cart.getDiscountedPrice(), cart.getTotalItemCost()));
            }
            boolean b = saveOrder(orderId, customerId, date, time, totalOrderCost, items);
            if (b) {
                if (normalPlaceOrderBO.ifIncomeExists()) {
                    updateIncome(totalOrderCost);
                } else {
                    IncomeDTO incomeDTO = new IncomeDTO(setDate, totalOrderCost, 0.0, totalOrderCost);
                    normalPlaceOrderBO.saveIncome(incomeDTO);
                }
                CommonFunctions.setNotificationSuccess("Order Placed", "Order Placed Successfully");
                printBill(orderId, txtCustomerName.getText());
                removeItemsFromTheCart();
                setOrderId();
                clearCustomerItemFields();
                placeOrderBtnId.setDisable(true);
                addToCartBtnId.setDisable(true);
                clearBtnId.setDisable(true);
                txtAddDiscount.setDisable(true);
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Placing " + orderId + " is Unsuccessful");
            }
        } catch (SQLException | JRException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean saveOrder(String orderId, String customerId, String date, String time, double totalOrderCost, ArrayList<ItemDetailsDTO> items) throws SQLException {
        NormalOrderDTO orderDTO = new NormalOrderDTO(orderId, customerId, date, time, totalOrderCost, items);
        return normalPlaceOrderBO.purchaseNormalOrder(orderDTO);
    }

    public void btnDiscardAction(ActionEvent actionEvent) {
        if (selectedIndex == -1) {
            new Alert(Alert.AlertType.WARNING, "Select a row from the Table", ButtonType.CLOSE).show();
        } else {
            tblAddToCartView.getItems().remove(selectedIndex);
            double cost = calculateTotalCostOfTheOrder();
            double orderCost = cost - calculateDiscountForEntireOrder();
            lblTotal.setText(String.valueOf(orderCost));

            double subTotal = calculateTotalCostOfTheOrder();
            lblSubTotal.setText(String.valueOf(subTotal));

            lblDiscount.setText(String.valueOf(calculateDiscountForEntireOrder()));
        }
    }

    public void removeItemsFromTheCart() throws SQLException {
        ObservableList<AddToCartTm> cartList = tblAddToCartView.getItems();
        for (int i = 0; i < cartList.size(); i++) {
            cartList.remove(i);
        }
    }

    public void printBill(String orderId, String customerName) throws JRException {
        ObservableList<AddToCartTm> cartList = tblAddToCartView.getItems();
        double cost = calculateTotalCostOfTheOrder();
        double total = cost - calculateDiscountForEntireOrder();

        double subTotal = calculateTotalCostOfTheOrder();

        double discount = calculateDiscountForEntireOrder();
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/NormalSalesInvoice.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        HashMap map = new HashMap();
        map.put("orderId", orderId);
        map.put("customerName", customerName);
        map.put("cashierName", cashierName);
        map.put("subTotal", subTotal);
        map.put("discount", discount);
        map.put("total", total);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(cartList.toArray()));
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void updateIncome(double cost) throws SQLException {
        IncomeDTO dto = new IncomeDTO(setDate, cost, 0.0, cost);
        if (normalPlaceOrderBO.updateIncome(dto)) {
            System.out.println("Income Updated");
        } else {
            System.out.println("Income doesnt updated successfully");
        }
    }
}
