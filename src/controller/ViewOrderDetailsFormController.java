package controller;

import bo.BOFactory;
import bo.custom.ViewOrderDetailsBO;
import dto.NormalOrderDTO;
import dto.RepairOrderDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewOrderDetailsFormController {
    public AnchorPane viewOrdersPane;
    public Label lblNormalOrderId;
    public Label lblNormalCustomerId;
    public Label lblNormalOrderDate;
    public Label lblNormalOrderTime;
    public Label lblNormalOrderCost;
    public TextField txtSearchNormalOrders;
    public Label lblRepairOrderId;
    public Label lblRepairCustomerId;
    public Label lblRepairOrderDate;
    public Label lblRepairOrderTime;
    public Label lblRepairOrderDiscount;
    public TextField txtSearchRepairOrders;
    public Label lblRepairOrderCost;
    public Label lblTotalItemsInTheOrder;
    public static String orderId;
    public static String repairOrderId;
    public Label lblRepairId;
    public Label labelCount;
    public AutoCompletionBinding<String> autoCompletionBinding;
    public ArrayList<String> orderIdList = new ArrayList<>();
    public ArrayList<String> repairOrderIdList = new ArrayList<>();
    private ViewOrderDetailsBO viewOrderDetailsBO = (ViewOrderDetailsBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.VIEWORDERDETAILS);

    public void initialize() {
        try {
            setDataToOrderIdList();
            setDataToRepairIdList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnViewNormalOrderDetails(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/NormalOrderItemDetailsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Normal Order Item Details Form");
        primaryStage.show();
    }

    public void btnViewRepairOrderDetails(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/RepairOrderDetailsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Repair Order Item Details Form");
        primaryStage.show();
    }

    public void getOrderDetailsEvent(KeyEvent keyEvent) throws SQLException {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchNormalOrders, orderIdList);
        switch (keyEvent.getCode()) {
            case SPACE:
                CommonFunctions.autoCompletionLearnWord(orderIdList, txtSearchNormalOrders.getText().trim(), autoCompletionBinding, txtSearchNormalOrders);
                break;
            case BACK_SPACE:
                clearLabelsOfNormalOrder();
                break;
            default:
                break;
        }
        orderId = txtSearchNormalOrders.getText();
        NormalOrderDTO order = viewOrderDetailsBO.searchNormalOrders(txtSearchNormalOrders.getText());
        if (orderId.length() == 5) {
            lblNormalOrderId.setText(order.getOrderId());
            lblNormalCustomerId.setText(order.getCustomerId());
            lblNormalOrderDate.setText(order.getOrderDate());
            lblNormalOrderTime.setText(order.getOrderTime());
            lblNormalOrderCost.setText(String.valueOf(order.getOrderCost()));

            setCountOfOrderItemDetails();
        } else {
            lblTotalItemsInTheOrder.setText("");
        }
    }

    public void setCountOfOrderItemDetails() throws SQLException {
        lblTotalItemsInTheOrder.setText(String.valueOf(viewOrderDetailsBO.getNormalOrderDetailsItemCount(txtSearchNormalOrders.getText())));
    }

    public void searchRepairEvent(KeyEvent keyEvent) throws SQLException {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchRepairOrders, repairOrderIdList);
        switch (keyEvent.getCode()) {
            case SPACE:
                CommonFunctions.autoCompletionLearnWord(repairOrderIdList, txtSearchRepairOrders.getText().trim(), autoCompletionBinding, txtSearchRepairOrders);
                break;
            case BACK_SPACE:
                clearRepairOrderLabels();
                break;
            default:
                break;
        }
        repairOrderId = txtSearchRepairOrders.getText();
        RepairOrderDTO repairOrderDetails = viewOrderDetailsBO.searchRepairOrders(txtSearchRepairOrders.getText());
        if (repairOrderId.length() == 6) {
            lblRepairOrderId.setText(repairOrderDetails.getOrderId());
            lblRepairId.setText(repairOrderDetails.getRepairId());
            lblRepairCustomerId.setText(repairOrderDetails.getCustomerId());
            lblRepairOrderDate.setText(repairOrderDetails.getOrderDate());
            lblRepairOrderTime.setText(repairOrderDetails.getOrderTime());
            lblRepairOrderDiscount.setText(String.valueOf(repairOrderDetails.getOrderDiscount()));
            lblRepairOrderCost.setText(String.valueOf(repairOrderDetails.getOrderCost()));

            setRepairOrderRepairsCount();
        } else {
            lblTotalItemsInTheOrder.setText(String.valueOf(0));
        }
    }

    public void clearLabelsOfNormalOrder() {
        lblNormalOrderId.setText("Order Id");
        lblNormalCustomerId.setText("Customer Id");
        lblNormalOrderCost.setText("Order Cost");
        lblNormalOrderTime.setText("Order Time");
        lblNormalOrderDate.setText("Order Date");
    }

    public void clearRepairOrderLabels() {
        lblRepairId.setText("Repair Id");
        lblRepairOrderId.setText("Repair Order Id");
        lblRepairCustomerId.setText("Repair Customer Id");
        lblRepairOrderCost.setText("Repair Order Cost");
        lblRepairOrderDiscount.setText("Repair Order Discount");
        lblRepairOrderTime.setText("Repair Order Time");
        lblRepairOrderDate.setText("Repair Order Date");
    }

    public void setRepairOrderRepairsCount() throws SQLException {
        int count = viewOrderDetailsBO.getRepairOrderRepairsCount(repairOrderId);
        lblTotalItemsInTheOrder.setText(String.valueOf(count));
        labelCount.setText("Repair Order Repairs Count");
    }

    public void setDataToOrderIdList() throws SQLException {
        orderIdList = viewOrderDetailsBO.getNormalOrderIds();
    }

    public void setDataToRepairIdList() throws SQLException {
        repairOrderIdList = viewOrderDetailsBO.getRepairOrderIds();
    }
}
