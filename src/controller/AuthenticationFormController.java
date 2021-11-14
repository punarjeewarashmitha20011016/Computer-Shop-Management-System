package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthenticationFormController {
    public AnchorPane otpView;
    public JFXPasswordField otpPassword;

    public void btnLogToCreateAdminAccount(ActionEvent actionEvent) throws IOException {
        if (otpPassword.getText().equals("admin123")){
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CreateNewAdminAccountForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage= (Stage) otpView.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Create Admin Account Form");
            primaryStage.show();
        }else {
            new Alert(Alert.AlertType.WARNING,"OTP Confirmation Unsuccessful", ButtonType.CLOSE).show();
        }
    }

    public void btnBackToLogin(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) otpView.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
