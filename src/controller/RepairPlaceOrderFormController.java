package controller;

import bo.BOFactory;
import bo.custom.RepairPlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.RepairDetailsTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CommonFunctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.LoginFormController.cashierName;
import static controller.RepairFinishedFromController.repairDetails;
import static util.CommonFunctions.setDate;

public class RepairPlaceOrderFormController {
    public AnchorPane repairMakeOrderPane;
    public TableView<RepairDetailsTm> tblAddToCart;
    public Label lblSetOrderId;
    public JFXTextField txtAddDiscount;
    public Label lblDiscount;
    public Label lblTotal;
    public Label lblSubTotal;
    public TableColumn tblRepairId;
    public TableColumn tblRepairItemDescription;
    public TableColumn tblRepairType;
    public TableColumn tblRepairPart;
    public TableColumn tblRepairCost;
    public TableColumn tblRepairCount;
    public JFXButton btnClearId;
    public JFXButton btnPlaceOrderId;
    public JFXButton btnDiscardId;
    private final ObservableList<RepairDetailsTm> addToCartObList = FXCollections.observableArrayList();
    public String repairId;
    public String repairOrderId;
    public String customerId;
    public String startDate;
    public String finishedDate;
    private int selectedIndex = -1;
    double discount = 0.0;
    private RepairPlaceOrderBO repairPlaceOrderBO = (RepairPlaceOrderBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.REPAIRPLACEORDER);

    public void initialize() {
        try {
            generateRepairOrderId();
            tblRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
            tblRepairCount.setCellValueFactory(new PropertyValueFactory<>("repairCount"));
            tblRepairItemDescription.setCellValueFactory(new PropertyValueFactory<>("repairItemDescription"));
            tblRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
            tblRepairPart.setCellValueFactory(new PropertyValueFactory<>("repairPart"));
            tblRepairCost.setCellValueFactory(new PropertyValueFactory<>("repairCost"));
            setDataToAddToCartObList();
            tblAddToCart.refresh();
            calculateCost();
            getCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblAddToCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex = (int) newValue;
        });

    }

    public void btnClear(ActionEvent actionEvent) {
        addToCartObList.remove(selectedIndex);
    }

    public void btnPlaceOrderAction(ActionEvent actionEvent) {
        try {
            setStartFinishedDate();
            String setDate = CommonFunctions.setDate;
            String setTime = CommonFunctions.setTime;

            ArrayList<RepairOrderDetailsDTO> repairItemDetails = new ArrayList<>();
            for (RepairDetailsTm r1 : tblAddToCart.getItems()
            ) {
                repairItemDetails.add(new RepairOrderDetailsDTO(lblSetOrderId.getText(), r1.getRepairCount(), r1.getRepairItemDescription(), r1.getRepairType(), r1.getRepairPart(), startDate, finishedDate, r1.getRepairCost()));
            }
            RepairOrderDTO repairOrder = new RepairOrderDTO(repairOrderId, repairId, customerId, setDate, setTime, Double.parseDouble(lblDiscount.getText()), Double.parseDouble(lblTotal.getText()), repairItemDetails);
            if (repairPlaceOrderBO.saveRepairOrder(repairOrder)) {
                if (repairPlaceOrderBO.ifIncomeExists()) {
                    updateIncome(repairOrder.getOrderCost());
                }else {
                    IncomeDTO incomeDTO=new IncomeDTO(setDate,0.0,repairOrder.getOrderCost(),repairOrder.getOrderCost());
                    repairPlaceOrderBO.saveIncome(incomeDTO);
                }
                printBill(repairOrder.getOrderId(), repairOrder.getCustomerId(), repairOrder.getOrderDiscount());
                clearAddToCartList();
                if (repairPlaceOrderBO.deleteFromRepairsFinished(repairId)) {
                    CommonFunctions.setNotificationSuccess("Repair order Placed", "Repair Order Placed Successfully");
                    generateRepairOrderId();
                    btnDiscardId.setDisable(true);
                    btnPlaceOrderId.setDisable(true);
                    btnClearId.setDisable(true);
                    clearFields();
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Placing Repair Order is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateIncome(double cost) throws SQLException {
        IncomeDTO dto = new IncomeDTO(setDate, 0.0, cost, cost);
        if (repairPlaceOrderBO.updateRepairSalesIncome(dto)) {
            System.out.println("Income Updated");
        } else {
            System.out.println("Income doesnt updated successfully");
        }
    }

    public void clearAddToCartList() {
        ObservableList<RepairDetailsTm> addToCartObList = tblAddToCart.getItems();
        for (int i = 0; i < addToCartObList.size(); i++) {
            if (repairId.equals(addToCartObList.get(i).getRepairId())) {
                addToCartObList.remove(i);
                i = 0;
            }
        }
    }

    public void clearFields() {
        lblTotal.setText(null);
        lblDiscount.setText(null);
        lblSubTotal.setText(null);
        txtAddDiscount.clear();
    }

    public void calculateCost() {
        double subTotal = 0.0;
        for (int i = 0; i < addToCartObList.size(); i++) {
            subTotal += addToCartObList.get(i).getRepairCost();
            repairId = addToCartObList.get(i).getRepairId();
        }
        if (txtAddDiscount.getText().isEmpty()) {
            discount = 0.0;
        }
        double calculatedDiscountOfTheOrder = subTotal * discount;
        double total = subTotal - calculatedDiscountOfTheOrder;
        lblSubTotal.setText(String.valueOf(subTotal));
        lblDiscount.setText(String.valueOf(calculatedDiscountOfTheOrder));
        lblTotal.setText(String.valueOf(total));
    }

    public void generateRepairOrderId() throws SQLException {
        String orderId = repairPlaceOrderBO.generateOrderId();
        lblSetOrderId.setText(orderId);
        repairOrderId = orderId;
    }

    public void getCustomerId() throws SQLException {
        System.out.println(repairId);
        ArrayList<RepairsFinishedDTO> repairsFinished = repairPlaceOrderBO.getAllRepairsFinishedById(repairId);
        System.out.println(repairsFinished.toString());
        for (int i = 0; i < repairsFinished.size(); i++) {
            System.out.println(repairsFinished.get(i).getCustomerId());
            customerId = repairsFinished.get(i).getCustomerId();
        }
    }

    public void btnDiscardAction(ActionEvent actionEvent) {
        for (int i = 0; i < addToCartObList.size(); i++) {
            addToCartObList.remove(i);
        }
    }

    public void setDataToAddToCartObList() {
        tblAddToCart.getItems().clear();
        ObservableList<RepairsFinishedDetailsDTO> repairDetailsObservableList = repairDetails;
        for (RepairsFinishedDetailsDTO details : repairDetailsObservableList
        ) {
            addToCartObList.add(new RepairDetailsTm(details.getRepairId(), details.getRepairCount(), details.getRepairItemDescription(), details.getRepairType(), details.getRepairPart(), details.getRepairCost()));
            btnClearId.setDisable(false);
            btnPlaceOrderId.setDisable(false);
            btnDiscardId.setDisable(false);
        }
        tblAddToCart.setItems(addToCartObList);
    }

    public void setStartFinishedDate() throws SQLException {
        ArrayList<RepairsFinishedDTO> allRepairsFinishedById = repairPlaceOrderBO.getAllRepairsFinishedById(repairId);
        for (RepairsFinishedDTO r1 : allRepairsFinishedById
        ) {
            startDate = r1.getRepairStartDate();
            finishedDate = r1.getRepairFinishedDate();
        }
    }

    public void addDiscountEvent(KeyEvent keyEvent) {
        double discountInPercentage = Double.parseDouble(txtAddDiscount.getText());
        discount = discountInPercentage / 100;
        System.out.println(discount);
        calculateCost();

    }

    public void printBill(String orderId, String customerId, double discount) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/RepairSalesReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            HashMap map = new HashMap();
            map.put("orderId", orderId);
            String customerName = getCustomerName(customerId);
            double subTotal = Double.parseDouble(lblSubTotal.getText());
            double total = Double.parseDouble(lblTotal.getText());
            map.put("customerName", customerName);
            map.put("cashierName", cashierName);
            map.put("subTotal", subTotal);
            map.put("discount", discount);
            map.put("total", total);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(addToCartObList.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCustomerName(String customerId) throws SQLException {
        CustomerDTO c1 = repairPlaceOrderBO.searchCustomers(customerId);
        String customerName = null;
        customerName = c1.getCustomerName();
        return customerName;
    }
}
