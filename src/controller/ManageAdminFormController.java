package controller;

import bo.BOFactory;
import bo.custom.ManageAdminBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.AdminDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.CommonFunctions;
import util.Validator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static util.CommonFunctions.setNotificationSuccess;
import static util.CommonFunctions.setNotificationWarning;
import static util.Validator.textFields;
import static util.Validator.txtField;

public class ManageAdminFormController {
    public Rectangle createAdminAccountView;
    public JFXTextField txtAdminId;
    public JFXTextField txtAdminName;
    public JFXTextField txtAdminNic;
    public Label lblSetAdminId;
    public JFXTextField txtAdminUserName;
    public JFXTextField txtAdminPassword;
    public JFXTextField txtAdminContactNo;
    public JFXButton createAccountBtnId;
    public JFXButton btnGenerateAdmin;
    public JFXButton btnChooseFileId;
    public JFXTextField txtChooseFileLocation;
    public ImageView imgSetAdminAvatar;
    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(A-){1}[0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{2,30}$");
    Pattern nicPattern = Pattern.compile("^([0-9]{12}|[0-9]{9}[V])$");
    Pattern contactNoPattern = Pattern.compile("^[0-9]{10}$");
    Pattern userNamePattern = Pattern.compile("^[A-z0-9.]{5,30}[@][A-z]{3,7}[.](com|lk|uk|[a-z]){1,}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9+.@_]{5,30}$");
    File file;
    private ManageAdminBO manageAdminBO = (ManageAdminBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.MANAGEADMIN);

    public void initialize() {
        textFields.clear();
        validateTextFields();
        new Validator(allValidations);
        try {
            setAdminIdToLable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setDataToTextField();
    }

    public void btnGenerateAdminId(ActionEvent actionEvent) {
        String id = null;
        try {
            id = generateAdminId();
            txtAdminId.setText(id);
            txtAdminId.setDisable(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        lblSetAdminId.setText(id + " : ");
    }

    public void btnCreateAccount(ActionEvent actionEvent) throws SQLException {
        String[] encryptedPasswordArray = encryptPassword();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < encryptedPasswordArray.length; i++) {
            stringBuffer.append(encryptedPasswordArray[i]);
        }
        String encryptPassword = stringBuffer.toString();
        System.out.println(encryptPassword);

        String imageLocation = null;
        if (file != null) {
            imageLocation = file.toURI().toString();
            imageLocation = imageLocation.replace("\\", "\\\\");
        } else {
            imageLocation = null;
        }

        AdminDTO adminDTO = new AdminDTO(txtAdminId.getText(), txtAdminName.getText(), txtAdminNic.getText(), txtAdminContactNo.getText(), txtAdminUserName.getText(), encryptPassword, imageLocation);
        if (manageAdminBO.ifAdminIdExists(txtAdminId.getText())) {
            CommonFunctions.setNotificationWarning("Try Again", "Already adminId exists");
            return;
        } else {
            ArrayList<AdminDTO> adminArrayList = getAdminDetailsToCheck();
            for (int i = 0; i < adminArrayList.size(); i++) {
                if (adminArrayList.get(i).getAdminNic().equals(txtAdminNic.getText())
                        || adminArrayList.get(i).getAdminContactNo().equals(txtAdminContactNo.getText())
                        || adminArrayList.get(i).getAdminUserName().equals(txtAdminUserName.getText())) {

                    if (adminArrayList.get(i).getAdminNic().equals(txtAdminNic.getText())) {
                        new Alert(Alert.AlertType.WARNING, " Nic already Exist. Please Try Again", ButtonType.CLOSE).show();
                        createAccountBtnId.setDisable(true);
                        return;
                    } else if (adminArrayList.get(i).getAdminContactNo().equals(txtAdminContactNo.getText())) {
                        new Alert(Alert.AlertType.WARNING, " Contact No already Exist. Please Try Again", ButtonType.CLOSE).show();
                        createAccountBtnId.setDisable(true);
                        return;
                    } else if (adminArrayList.get(i).getAdminUserName().equals(txtAdminUserName.getText())) {
                        new Alert(Alert.AlertType.WARNING, " User Name or Password is already Exist. Please Try Again", ButtonType.CLOSE).show();
                        createAccountBtnId.setDisable(true);
                        return;
                    } else {
                        new Alert(Alert.AlertType.WARNING, " Login Credentials are already Exist. Please Try Again", ButtonType.CLOSE).show();
                        createAccountBtnId.setDisable(true);
                        return;
                    }
                }
            }
            if (manageAdminBO.saveAdmin(adminDTO)) {
                setNotificationSuccess("Account Created", "Admin Account Created Successfully");
                clearTextFields();
                CommonFunctions.setDisableFields(allValidations);
            } else {
                setNotificationWarning("Try Again", "Admin Account creation is unsuccessful");
            }
        }
    }

    public ArrayList<AdminDTO> getAdminDetailsToCheck() throws SQLException {
        ArrayList<AdminDTO> all = manageAdminBO.getAll();
        return all;
    }

    public String[] encryptPassword() {
        String password = txtAdminPassword.getText();
        int key = 2;
        char[] chars = password.toCharArray();
        String[] chrs = new String[txtAdminPassword.getLength()];
        int index = 0;
        for (char c : chars
        ) {
            c += key;
            chrs[index] = String.valueOf(c);
            index++;
        }
        for (int i = 0; i < chrs.length; i++) {
            System.out.println(chrs[i]);
        }
        return chrs;
    }

    public void btnBackToLogin(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) createAdminAccountView.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String generateAdminId() throws SQLException {
        return manageAdminBO.getAdminId();
    }

    public void validateTextFields() {
        allValidations.put(txtAdminId, idPattern);
        allValidations.put(txtAdminName, namePattern);
        allValidations.put(txtAdminNic, nicPattern);
        allValidations.put(txtAdminContactNo, contactNoPattern);
        allValidations.put(txtAdminUserName, userNamePattern);
        allValidations.put(txtAdminPassword, passwordPattern);
    }

    public void validateAdminTextFieldEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(allValidations, createAccountBtnId);
        if (response instanceof Boolean) {
            btnGenerateAdmin.setDisable(false);
            txtField.setDisable(false);
        }
        if (keyEvent.getCode() == KeyCode.TAB) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
    }

    public void clearTextFields() {
        CommonFunctions.clearFields(allValidations);
        txtChooseFileLocation.clear();
        imgSetAdminAvatar.setImage(null);
    }

    public void setAdminIdToLable() throws SQLException {
        String id = generateAdminId();
        lblSetAdminId.setText(id);
    }

    public void btnChooseFile(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();
        String fileName = file.getAbsolutePath();
        txtChooseFileLocation.setText(fileName);
        System.out.println(file.toURI().toString());
        Image img = new Image(file.toURI().toString());
        imgSetAdminAvatar.setImage(img);
    }

    public void btnUpdate(ActionEvent actionEvent) {
        String location = txtChooseFileLocation.getText();
        String[] encryptPassword = encryptPassword();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < encryptPassword.length; i++) {
            stringBuffer.append(encryptPassword[i]);
        }
        String password = stringBuffer.toString();
        try {
            AdminDTO adminDTO = new AdminDTO(txtAdminId.getText(), txtAdminName.getText(), txtAdminNic.getText(), txtAdminContactNo.getText(), txtAdminUserName.getText(), password, location);
            if (manageAdminBO.ifAdminIdExists(txtAdminId.getText())) {
                if (manageAdminBO.updateAdmin(adminDTO)) {
                    CommonFunctions.setNotificationSuccess("Admin Details Updated", "Admin Details Updated Successfully");
                    CommonFunctions.setDisableFields(allValidations);
                    clearTextFields();
                    setAdminIdToLable();
                } else {
                    CommonFunctions.setNotificationWarning(
                            "Try Again", "Updating AdminLogin Details is Unsuccessful"
                    );
                }
            } else {
                CommonFunctions.setNotificationWarning("Try Again", "Admin ID doesn't exists");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToTextField() {
        txtAdminId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("listener");
                CommonFunctions.setEnableTextFields(allValidations);
                try {
                    AdminDTO adminDTO = manageAdminBO.searchAdmin(txtAdminId.getText());
                    txtChooseFileLocation.setDisable(false);
                    String decryptedPassword = decryptedPassword(adminDTO.getAdminPassword());
                    txtAdminName.setText(adminDTO.getAdminName());
                    txtAdminNic.setText(adminDTO.getAdminNic());
                    txtAdminContactNo.setText(adminDTO.getAdminContactNo());
                    txtAdminUserName.setText(adminDTO.getAdminUserName());
                    txtAdminPassword.setText(decryptedPassword);
                    txtChooseFileLocation.setText(adminDTO.getAdminImage());

                    if (!manageAdminBO.ifAdminIdExists(txtAdminId.getText())) {
                        txtAdminId.setDisable(false);
                        txtChooseFileLocation.setDisable(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String decryptedPassword(String password) {
        String encryptedPassword = password;
        char[] chars = encryptedPassword.toCharArray();
        String[] s = new String[chars.length];
        int index = 0;
        int key = 2;
        for (char c : chars
        ) {
            c -= key;
            s[index] = String.valueOf(c);
            index++;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < s.length - 1; i++) {
            stringBuffer.append(s[i]);
        }
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }
}
