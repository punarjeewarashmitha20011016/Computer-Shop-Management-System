package controller;

import bo.BOFactory;
import bo.custom.AdminBO;
import bo.custom.ManageAdminBO;
import dto.ItemDTO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
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
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import util.CommonFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import static controller.LoginFormController.adminName;

public class AdminFormController {
    public BarChart<String, Number> barChart;
    public BorderPane adminFormPane;
    public Label lblDate;
    public Label lblTime;
    public Label lblSetName;
    public AnchorPane childPane;
    public ImageView image;
    public ImageView childSawImage;
    public Label lblSetTotalNormalOrdersCount;
    public Label lblSetTotalIncome;
    public Label lblSetTotalRepairOrdersCount;
    public Label lblSetMostSellingItem;
    public Label lblSetTotalSalesToday;
    public Label lblSetTotalRepairsToday;
    public Label lblSetDailyRepairIncome;
    public Label lblSetDailyNormalOrderIncome;
    public Label lblSetTotalDailyIncome;

    public double repairDailyIncome;
    public double normalDailyIncome;
    public Circle circle;
    public int CountNotifications;
    public Label lblNotificationCount;
    public static ArrayList<Label> labelArrayListNotifications = new ArrayList<>();
    private AdminBO adminBO = (AdminBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.ADMIN);

    public void initialize() {
        try {
            setAvatar();
            CommonFunctions.loadDateAndTime(lblDate, lblTime);
            setAdminName();
            animationCogWheel();
            setNotificationWhenItemsAreLessThanFive();

            setTotalOrders();
            setTotalRepairOrders();
            todayNormalSales();
            todayRepairSales();
            setTotalIncome();
            setMostSellingItem();

            setDailyNormalOrderIncome();
            setDailyRepairOrderIncome();
            setDailyTotalIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setAdminName() {
        lblSetName.setText("Welcome - " + adminName);
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) adminFormPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void btnHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/AdminForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) childPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void btnManageCashier(ActionEvent actionEvent) {
        loadUi("ManageCashierForm");
    }

    public void btnManageItems(ActionEvent actionEvent) {
        loadUi("ManageItemsForm");
    }

    public void btnSystemReports(ActionEvent actionEvent) {
        loadUi("ViewIncomeForm");
    }

    public void setNotificationWhenItemsAreLessThanFive() throws SQLException {
        labelArrayListNotifications.clear();
        ArrayList<ItemDTO> itemsRemaining = adminBO.getLowestRemainingItemsInTheStock();
        for (int i = 0; i < itemsRemaining.size(); i++) {
            Label setNotificationsToLabel = new Label();
            setNotificationsToLabel.setStyle("-fx-text-fill: white;");
            setNotificationsToLabel.setStyle("-fx-font-size: 55");

            String title = "Alert - Listed Item is less than 5";
            String message = "Check " + itemsRemaining.get(i).getItemDescription() + " item in the Stock";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
            CountNotifications++;
            setNotificationsToLabel.setText(message);
            labelArrayListNotifications.add(setNotificationsToLabel);
        }
        lblNotificationCount.setText(String.valueOf(CountNotifications));
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

    public void setTotalOrders() throws SQLException {
        lblSetTotalNormalOrdersCount.setText(String.valueOf(adminBO.getAllNormalOrdersCount()));
    }

    public void setTotalRepairOrders() throws SQLException {
        lblSetTotalRepairOrdersCount.setText(String.valueOf(adminBO.getAllRepairOrdersCount()));
    }

    public void todayNormalSales() throws SQLException {
        lblSetTotalSalesToday.setText(String.valueOf(adminBO.getTotalOrdersPerDay()));
    }

    public void todayRepairSales() throws SQLException {
        lblSetTotalRepairsToday.setText(String.valueOf(adminBO.getTotalRepairOrdersPerDay()));
    }

    public void setTotalIncome() throws SQLException {
        double normalSales=adminBO.getTotalNormalOrdersIncome();
        double repairSales=adminBO.getTotalRepairSalesIncome();
        CommonFunctions.setTotalIncome(lblSetTotalIncome,normalSales,repairSales);
    }

    public void setMostSellingItem() throws SQLException {
        String itemDescription = adminBO.getMostSellingItem();
        lblSetMostSellingItem.setText(itemDescription);
    }

    public void setDailyNormalOrderIncome() throws SQLException {
        normalDailyIncome = adminBO.getDailyNormalOrderIncome();
        lblSetDailyNormalOrderIncome.setText(String.valueOf(normalDailyIncome));
    }

    public void setDailyRepairOrderIncome() throws SQLException {
        repairDailyIncome = adminBO.getDailyRepairOrderIncome();
        lblSetDailyRepairIncome.setText(String.valueOf(repairDailyIncome));
    }

    public void setDailyTotalIncome() {
        double total = repairDailyIncome + normalDailyIncome;
        lblSetTotalDailyIncome.setText(String.valueOf(total));
    }

    public void setAvatar() throws SQLException {
        Image image = new Image(adminBO.getAdminAvatar());
        circle.setFill(new ImagePattern(image));

    }

    public void notificationBellIconActionEvent(MouseEvent mouseEvent) {
        loadUi("NotificationsForm");
    }
}
