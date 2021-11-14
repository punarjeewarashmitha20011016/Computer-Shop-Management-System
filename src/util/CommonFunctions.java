package util;

import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CommonFunctions {
    public static String setDate;
    public static String setTime;
    public static void setNotificationSuccess(String buttonTitle, String functionMessage) {
        String title = buttonTitle;
        String message = functionMessage;
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.millis(8000));
    }

    public static void setNotificationWarning(String buttonTitle, String functionMessage) {
        String title = buttonTitle;
        String message = functionMessage;
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.WARNING);
        notification.showAndDismiss(Duration.millis(8000));
    }

    public static void clearFields(LinkedHashMap<TextField, Pattern> txt) {
        for (TextField textFields : txt.keySet()
        ) {
            textFields.clear();
        }
    }

    public static void setDisableFields(LinkedHashMap<TextField, Pattern> txt) {
        for (TextField text : txt.keySet()
        ) {
            text.setDisable(true);
        }
    }

    public static void setEnableTextFields(LinkedHashMap<TextField, Pattern> txt) {
        for (TextField textField : txt.keySet()
        ) {
            textField.setDisable(false);
        }

    }

    public static void loadDateAndTime(Label lblDate, Label lblTime) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String txtDate = simpleDateFormat.format(date);
        lblDate.setText(txtDate);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        setDate = simpleDateFormat1.format(date);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
                                lblTime.setText(simpleDateFormat.format(time.getTime()));
                                setTime = simpleDateFormat.format(time.getTime());
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void setTotalIncome(Label lblSetTotalIncome,double normalSalesIncome,double repairSalesIncome) {
        String date = setDate;
        try {
            double normalSales = normalSalesIncome;
            double repairSales = repairSalesIncome;
            double total = normalSales + repairSales;

            PreparedStatement preparedStatement1 = DbConnection.getInstance().getConnection().prepareStatement("SELECT dateAsPerIncome,totalIncome FROM Income order by dateAsPerIncome desc limit 1 ");
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                double totalIncome = total + resultSet.getDouble(2);
                if (lblSetTotalIncome != null) {
                    lblSetTotalIncome.setText("Rs. " + resultSet.getDouble(2));
                }
                if (!resultSet.getString(1).equals(date)) {
                    PreparedStatement preparedStatement = setDataToParameters(date, normalSales, repairSales);
                    preparedStatement.setObject(4, totalIncome);
                    if (preparedStatement.executeUpdate() > 0) {
                        System.out.println("Income Saved");
                    } else {
                        System.out.println("Saving is unsuccessful due to duplicate entry");
                    }
                }
            } else {
                if (total == 0) {
                    lblSetTotalIncome.setText("");
                    lblSetTotalIncome.setText("Rs. " + total);
                    PreparedStatement preparedStatement = setDataToParameters(date, normalSales, repairSales);
                    preparedStatement.setObject(4, total);
                    if (preparedStatement.executeUpdate() > 0) {
                        System.out.println("Income Saved");
                    } else {
                        System.out.println("Saving is unsuccessful due to duplicate entry");
                    }
                    System.out.println("Try Again");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static PreparedStatement setDataToParameters(String date, double normalSales, double repairSales) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Income VALUES(?,?,?,?)");
            preparedStatement.setObject(1, date);
            preparedStatement.setObject(2, normalSales);
            preparedStatement.setObject(3, repairSales);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    public static void autoCompletionLearnWord(ArrayList<String> list, String
            trim, AutoCompletionBinding<String> autoCompletionBinding, TextField txtSearch) {
        list.add(trim);
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearch, list);
    }
}
