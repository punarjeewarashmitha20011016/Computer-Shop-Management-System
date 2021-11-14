package controller;

import bo.BOFactory;
import bo.custom.CashierBO;
import dto.RepairsFinishedDTO;
import dto.RepairsInProgressDTO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CommonFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import static controller.LoginFormController.cashierName;

public class CashierFormController {
    public BorderPane cashierFormPane;
    public Label lblSetName;
    public Label lblDate;
    public Label lblTime;
    public ImageView image;
    public AnchorPane childPane;
    public ImageView childSawImage;
    public Label lblOrdersCount;
    public Label lblRegisteredCustomersInteractToday;
    public Label lblRepairsCount;
    public Label lblTotalCustomerCount;
    public Label lblTotalSalesToday;
    public Label lblRepairsToday;
    public Label lblSetPendingRepairsCount;
    public Label lblSetRepairsFinishedCount;
    public Label lblSetLatRepairPlacedDateAndTime;
    public Circle circle;
    public Label lblNotificationCount;
    public static ArrayList<Label> labelArrayListCashier = new ArrayList<>();
    private CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.CASHIER);

    public void initialize() {
        try {
            setImageInCashierForm();
            CommonFunctions.loadDateAndTime(lblDate, lblTime);
            lblSetName.setText("WELCOME - " + cashierName);
            setOrderCountToLabel();
            setTodaySales();
            setTotalCustomersRegistered();
            setTotalCustomersInteractToday();
            animationCogWheel();
            setTotalRepairOrdersCount();
            totalRepairCountToday();
            setTotalIncome();
            setTotalRepairsInProgressCount();
            setTotalRepairFinishedCount();
            setLastRepairPlacedDateAndTime();
            getNotifications();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CashierForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) childPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnPos(ActionEvent actionEvent) {
        loadUi("NormalOrdersForm");
    }

    public void btnRepairItems(ActionEvent actionEvent) {
        loadUi("RepairItemsForm");
    }

    public void btnStock(ActionEvent actionEvent) {
        loadUi("StockForm");
    }

    public void btnReturns(ActionEvent actionEvent) {
        loadUi("ReturnsForm");
    }

    public void btnCustomer(ActionEvent actionEvent) {
        loadUi("ViewCustomerForm");
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) cashierFormPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void loadUi(String fileName) {
        URL resource = getClass().getResource("../view/" + fileName + ".fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
            childPane.getChildren().clear();
            childPane.getChildren().addAll(load);
            animationCogWheel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animationCogWheel() {
        Timeline rotate = new Timeline();
        Timeline rotate1 = new Timeline();
        DoubleProperty r = image.rotateProperty();
        DoubleProperty r1 = childSawImage.rotateProperty();

        rotate1.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r, -180))
        );
        rotate.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r1, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r1, 180))
        );
        rotate1.play();
        rotate.play();
    }

    public void btnViewOrders(ActionEvent actionEvent) {
        loadUi("ViewOrderDetailsForm");
    }

    public void setOrderCountToLabel() throws SQLException {
        lblOrdersCount.setText(String.valueOf(cashierBO.getTotalNormalOrdersCount()));
    }

    public void setTodaySales() throws SQLException {
        lblTotalSalesToday.setText(String.valueOf(cashierBO.countOfNormalOrderDailySales()));
    }

    public void setTotalCustomersInteractToday() throws SQLException {
        lblRegisteredCustomersInteractToday.setText(String.valueOf(cashierBO.getTotalCustomersInteractToday()));
    }

    public void setTotalCustomersRegistered() throws SQLException {
        lblTotalCustomerCount.setText(String.valueOf(cashierBO.getTotalCustomersRegistered()));
    }

    public void setTotalRepairOrdersCount() throws SQLException {
        lblRepairsCount.setText(String.valueOf(cashierBO.getTotalRepairOrdersCount()));
    }

    public void totalRepairCountToday() throws SQLException {
        lblRepairsToday.setText(String.valueOf(cashierBO.getDailyRepairOrdersCount()));
    }

    public void setTotalIncome() throws SQLException {
        double normalSalesIncome = cashierBO.getTotalNormalOrdersIncome();
        double repairSalesIncome = cashierBO.getTotalRepairSalesIncome();
        Label income = null;
        CommonFunctions.setTotalIncome(income, normalSalesIncome, repairSalesIncome);
    }

    public void setTotalRepairsInProgressCount() throws SQLException {
        int count = cashierBO.totalRepairsInProgressCount();
        lblSetPendingRepairsCount.setText(String.valueOf(count));
    }

    public void setTotalRepairFinishedCount() throws SQLException {
        int count = cashierBO.totalRepairsFinishedCount();
        lblSetRepairsFinishedCount.setText(String.valueOf(count));
    }

    public void setLastRepairPlacedDateAndTime() throws SQLException {
        String dateAndTime = cashierBO.getLastRepairPlacedDateAndTime();
        lblSetLatRepairPlacedDateAndTime.setText(dateAndTime);
    }

    public void setImageInCashierForm() throws SQLException {
        Image image = new Image(cashierBO.getCashierAvatar());
        circle.setFill(new ImagePattern(image));
    }

    public void getNotifications() throws SQLException {
        int count = 0;
        ArrayList<RepairsInProgressDTO> repairsInProgressDTOS = cashierBO.getAllRepairsInProgress();
        ArrayList<RepairsFinishedDTO> repairsFinishedDTOS = cashierBO.getAllRepairsFinished();
        for (int i = 0; i < repairsInProgressDTOS.size(); i++) {
            Label label = new Label();
            label.setStyle("-fx-text-fill: white;");
            label.setStyle("-fx-font-size: 55");
            count++;
            lblNotificationCount.setText(String.valueOf(count));
            label.setText("Check Repair Id " + repairsInProgressDTOS.get(i).getRepairId() + " in the Repairs In Progress Section");
            labelArrayListCashier.add(label);
        }

        for (int i = 0; i < repairsFinishedDTOS.size(); i++) {
            Label label = new Label();
            label.setStyle("-fx-text-fill: white;");
            label.setStyle("-fx-font-size: 55");
            count++;
            lblNotificationCount.setText(String.valueOf(count));
            label.setText("Check Repair Id " + repairsFinishedDTOS.get(i).getRepairId() + " in the Repairs Finished Section");
            labelArrayListCashier.add(label);
        }
    }

    public void notificationBellIconActionEvent(MouseEvent mouseEvent) {
        loadUi("NotificationsForm");
    }
}
