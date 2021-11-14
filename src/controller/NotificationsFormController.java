package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static controller.AdminFormController.labelArrayListNotifications;
import static controller.CashierFormController.labelArrayListCashier;

public class NotificationsFormController {
    public Rectangle notificationsPane;
    public ArrayList<Label> labelArrayList = new ArrayList<>();
    public ArrayList<Label> labelArrayListOfCashier = new ArrayList<>();
    public VBox vbox;
    public AnchorPane parentPaneOfVbox;

    public void initialize() {
        parentPaneOfVbox.setStyle("-fx-background-color: linear-gradient(#818486,#8d8888)");
        setDataToList();
        setDataToCashierLabelArrayList();

        setNotificationsOfAdmin();
        setNotificationsOfCashier();
    }

    private void setDataToList() {
        for (javafx.scene.control.Label lbl : labelArrayListNotifications
        ) {
            labelArrayList.add(lbl);
        }
    }

    public void setDataToCashierLabelArrayList() {
        for (javafx.scene.control.Label lbl : labelArrayListCashier
        ) {
            labelArrayListOfCashier.add(lbl);
        }
    }

    public void setNotificationsOfAdmin() {
        for (int i = 0; i < labelArrayList.size(); i++) {
            if (i % 2 == 0) {
                labelArrayList.get(i).setStyle("-fx-background-color: linear-gradient(dimgrey,darkgray);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x:50;-fx-padding: 20;");
                setDataToLabelsInNotificationsForm(labelArrayList, i);
            } else {
                labelArrayList.get(i).setStyle("-fx-background-color: linear-gradient(slategrey,dimgrey);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x: 50;-fx-padding: 20;");
                setDataToLabelsInNotificationsForm(labelArrayList, i);
            }
        }
    }

    public void setNotificationsOfCashier() {
        for (int i = 0; i < labelArrayListOfCashier.size(); i++) {
            if (i % 2 == 0) {
                labelArrayListOfCashier.get(i).setStyle("-fx-background-color: linear-gradient(dimgrey,darkgray);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x:50;-fx-padding: 20;");
                setDataToLabelsInNotificationsForm(labelArrayListOfCashier, i);
            } else {
                labelArrayListOfCashier.get(i).setStyle("-fx-background-color: linear-gradient(slategrey,dimgrey);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x: 50;-fx-padding: 20;");
                setDataToLabelsInNotificationsForm(labelArrayListOfCashier, i);
            }
        }
    }

    public void setDataToLabelsInNotificationsForm(ArrayList<Label> arrayList, int i) {
        arrayList.get(i).setPrefWidth(950);
        arrayList.get(i).setPrefHeight(63);
        JFXButton button = new JFXButton("Clear");
        button.setStyle("-fx-translate-x: 930;-fx-pref-height: 74;-fx-pref-width: 78.0;-fx-background-color:darkslategrey;-fx-text-fill: white;-fx-font-size: 18;");
        AnchorPane paneOne = new AnchorPane(arrayList.get(i), button);
        AnchorPane paneOneDuplicate = new AnchorPane(new javafx.scene.control.Label(" "));
        vbox.getChildren().addAll(paneOne);
        vbox.getChildren().addAll(paneOneDuplicate);
        clearNotification(arrayList.get(i), button, arrayList);
    }

    public void clearNotification(Label label, JFXButton button, ArrayList<Label> list) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (labelArrayList.equals(list)) {
                    for (int i = 0; i < labelArrayList.size(); i++) {
                        if (label.equals(labelArrayList.get(i))) {
                            labelArrayList.remove(i);
                            vbox.getChildren().clear();
                            setNotificationsOfAdmin();
                            return;
                        }
                    }
                }else if (labelArrayListOfCashier.equals(list)){
                    for (int i = 0; i < labelArrayListOfCashier.size(); i++) {
                        if (label.equals(labelArrayListOfCashier.get(i))){
                            labelArrayListOfCashier.remove(i);
                            vbox.getChildren().clear();;
                            setNotificationsOfCashier();
                            return;
                        }
                    }
                }
            }
        });
    }
}

