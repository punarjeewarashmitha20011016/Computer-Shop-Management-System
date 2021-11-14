package controller;

import bo.BOFactory;
import bo.custom.LoginBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.AdminDTO;
import dto.CashierDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public Rectangle loginView;
    public String adminUserName;
    public String adminDecryptPassword;
    public static String adminName;

    public String cashierUserName;
    public String cashierDecryptedPassword;
    public static String cashierName;
    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.LOGIN);

    public void btnCreateAnAdminAccount(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/AuthenticationForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) loginView.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Confirm Navigation Form");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void btnLogin(ActionEvent actionEvent) throws IOException, SQLException {
        if (loginBO.adminUserNameExists(txtUsername.getText())) {
            loadAdminLoginDetails();
        }
        if (loginBO.cashierUserNameExists(txtUsername.getText())) {
            loadCashierLoginDetails();
        }

        if (txtUsername.getText().equals(adminUserName) && txtPassword.getText().equals(adminDecryptPassword)) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/AdminForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) loginView.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin Form");
            primaryStage.centerOnScreen();
            primaryStage.show();
        } else if (txtUsername.getText().equals(cashierUserName) && txtPassword.getText().equals(cashierDecryptedPassword)) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CashierForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) loginView.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cashier Form");
            primaryStage.centerOnScreen();
            primaryStage.show();
        } else {
            new Alert(Alert.AlertType.WARNING, "User Name or Password is incorrect", ButtonType.CLOSE).show();
        }
    }

    public void loadAdminLoginDetails() throws SQLException {
        ResultSet resultSet = null;
        ArrayList<AdminDTO> allAdmins = loginBO.getAllAdmins();
        for (int i = 0; i < allAdmins.size(); i++) {
            String pass = null;
            int key = 2;
            if (txtUsername.getText().equals(allAdmins.get(i).getAdminUserName())) {
                pass = allAdmins.get(i).getAdminPassword();
                adminUserName = allAdmins.get(i).getAdminUserName();
                adminName = allAdmins.get(i).getAdminName();
            }
            //storing string variable into a char array pne by one
            char[] convertingToChar = pass.toCharArray();
            int index = 0;
            //storing char values in the char array into a string array by converting the encrypted password into a decrypted one
            String[] decryptedPassword = new String[convertingToChar.length];
            for (char c : convertingToChar
            ) {
                c -= key;
                decryptedPassword[index] = String.valueOf(c);
                index++;
            }
            //converting stored string array values to a single string variable
            StringBuffer strBuffer = new StringBuffer();
            for (int j = 0; j < decryptedPassword.length; j++) {
                strBuffer.append(decryptedPassword[j]);
            }
            //converted decrypted string variable
            adminDecryptPassword = strBuffer.toString();
            System.out.println(adminDecryptPassword);
        }
    }

    public void loadCashierLoginDetails() throws SQLException {
        String encryptedPassword = null;
        ArrayList<CashierDTO> allCashiers = loginBO.getAllCashiers();
        for (int i = 0; i < allCashiers.size(); i++) {
            if (txtUsername.getText().equals(allCashiers.get(i).getCashierUserName())) {
                cashierUserName = allCashiers.get(i).getCashierUserName();
                encryptedPassword = allCashiers.get(i).getCashierPassword();
                cashierName = allCashiers.get(i).getCashierName();
            }
            char[] chars = encryptedPassword.toCharArray();
            int key = 2;
            int index = 0;
            String[] decryptedPassword = new String[chars.length];
            for (char c : chars
            ) {
                c -= key;
                decryptedPassword[index] = String.valueOf(c);
                index++;
            }
            StringBuffer stringBuffer = new StringBuffer();

            for (int j = 0; j < decryptedPassword.length; j++) {
                stringBuffer.append(decryptedPassword[j]);
            }
            cashierDecryptedPassword = stringBuffer.toString();
            System.out.println(cashierDecryptedPassword);
        }
    }
}
