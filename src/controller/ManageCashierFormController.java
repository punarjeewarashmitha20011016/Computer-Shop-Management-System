package controller;

import bo.BOFactory;
import bo.custom.ManageCashierBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CashierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.CommonFunctions;
import util.Validator;
import view.tm.CashierDetailsTm;

import javax.swing.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.CommonFunctions.setNotificationSuccess;
import static util.CommonFunctions.setNotificationWarning;
import static util.Validator.textFields;
import static util.Validator.txtField;

public class ManageCashierFormController {
    public AnchorPane manageCashierPane;
    public JFXTextField txtCashierId;
    public JFXTextField txtCashierName;
    public JFXTextField txtCashierNic;
    public JFXTextField txtCashierContactNo;
    public JFXTextField txtCashierUserName;
    public JFXTextField txtCashierPassword;
    public TableView<CashierDetailsTm> tblCashierDetailsView;
    public TableColumn tblCashierId;
    public TableColumn tblCashierName;
    public TableColumn tblCashierNic;
    public TableColumn tblCashierContactNo;
    public TableColumn tblCashierUserName;
    public TableColumn tblCashierPassword;
    public TextField txtSearchCashier;
    public JFXButton addCashierBtn;
    public JFXButton updateCashierButton;
    public JFXButton deleteCashierButton;
    public JFXButton btnChooseFileID;
    public ImageView imgSetCashierImage;
    ObservableList<CashierDetailsTm> cashierDetailsObservableList = FXCollections.observableArrayList();
    int selectedRowIndex = -1;
    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(CA-){1}[0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{2,30}$");
    Pattern nicPattern = Pattern.compile("^([0-9]{12}|[0-9]{9}[V])$");
    Pattern contactNoPattern = Pattern.compile("^([0][0-9]{9})$");
    Pattern userNamePattern = Pattern.compile("^[A-z0-9.]{5,30}[@][A-z]{3,7}[.](com|lk|uk|[a-z]){1,}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9+.@_]{5,30}$");

    File file;
    private ManageCashierBO cashierBO = (ManageCashierBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.MANAGECASHIER);


    public void initialize() throws SQLException {
        textFields.clear();
        setCashierIdToTextField();
        validateTextFields();
        new Validator(allValidations);

        tblCashierId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        tblCashierName.setCellValueFactory(new PropertyValueFactory<>("cashierName"));
        tblCashierNic.setCellValueFactory(new PropertyValueFactory<>("cashierNic"));
        tblCashierContactNo.setCellValueFactory(new PropertyValueFactory<>("cashierContactNo"));
        tblCashierUserName.setCellValueFactory(new PropertyValueFactory<>("cashierUserName"));
        tblCashierPassword.setCellValueFactory(new PropertyValueFactory<>("cashierPassword"));
        try {
            setCashierDetailsToTheTable();
            tblCashierDetailsView.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        searchCashierFromTheTable();
        tblCashierDetailsView.refresh();

        selectRowFromTheTable();
    }

    public void selectRowFromTheTable() {
        tblCashierDetailsView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            CommonFunctions.setEnableTextFields(allValidations);
            selectedRowIndex = (int) newValue;
            txtCashierId.setText(cashierDetailsObservableList.get((Integer) newValue).getCashierId());
            txtCashierName.setText(cashierDetailsObservableList.get((Integer) newValue).getCashierName());
            txtCashierNic.setText(cashierDetailsObservableList.get((Integer) newValue).getCashierNic());
            txtCashierContactNo.setText(String.valueOf(cashierDetailsObservableList.get((Integer) newValue).getCashierContactNo()));
            txtCashierUserName.setText(cashierDetailsObservableList.get((Integer) newValue).getCashierUserName());
            String decryptedPassword = decryptPassword(cashierDetailsObservableList.get((Integer) newValue).getCashierPassword());
            txtCashierPassword.setText(decryptedPassword);
            try {
                String l = setImageToLocation((Integer) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    private String setImageToLocation(int newValue) throws SQLException {
        ResultSet resultSet = null;
        String location = null;
        CashierDTO cashierDTO = cashierBO.searchCashier(tblCashierDetailsView.getItems().get(newValue).getCashierId());
        if (cashierBO.ifCashierIdExists(cashierDTO.getCashierId())) {
            if (cashierDTO.getImageLocation() == null) {
                imgSetCashierImage.setImage(null);
            } else {
                Image image = new Image(cashierDTO.getImageLocation());
                imgSetCashierImage.getProperties().clear();
                imgSetCashierImage.setImage(image);
                return location = cashierDTO.getImageLocation();
            }

        } else {
            Image image = new Image(file.toURI().toString());
            imgSetCashierImage.setImage(image);
            imgSetCashierImage.setImage(image);
            return location = file.toURI().toString();
        }
        return null;
    }

    public String decryptPassword(String password) {
        System.out.println(password);
        String encryptPassword = password;
        int key = 2;
        int index = 0;
        char[] chars = encryptPassword.toCharArray();
        String[] strings = new String[chars.length];
        for (char c : chars
        ) {
            c -= key;
            strings[index] = String.valueOf(c);
            index++;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            stringBuffer.append(strings[i]);
        }
        String decryptedPassword = stringBuffer.toString();
        System.out.println(decryptedPassword);
        return decryptedPassword;
    }

    public void getAllFromCashier(ActionEvent actionEvent) throws SQLException {
        CashierDTO cashierDTO = cashierBO.searchCashier(txtCashierId.getText());
        txtCashierName.setText(cashierDTO.getCashierName());
        txtCashierNic.setText(cashierDTO.getCashierNic());
        txtCashierContactNo.setText(cashierDTO.getCashierContactNo());
        txtCashierUserName.setText(cashierDTO.getCashierUserName());
        txtCashierPassword.setText(cashierDTO.getCashierPassword());
    }

    public String generateCashierId() throws SQLException {
        return cashierBO.getCashierId();
    }

    public void btnAddCashier(ActionEvent actionEvent) throws SQLException {
        String[] encryptedPassword = encryptCashierPassword();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < encryptedPassword.length; i++) {
            stringBuffer.append(encryptedPassword[i]);
        }
        String cashierEncryptedPassword = stringBuffer.toString();
        System.out.println(cashierEncryptedPassword);

        String imageLocation = null;
        if (file != null) {
            imageLocation = file.toURI().toString();
            imageLocation = imageLocation.replace("\\", "\\\\");
        } else {
            imageLocation = null;
        }

        CashierDTO cashierDTO = new CashierDTO(txtCashierId.getText(), txtCashierName.getText(), txtCashierNic.getText(), txtCashierContactNo.getText(), txtCashierUserName.getText(), cashierEncryptedPassword, imageLocation);
        ArrayList<CashierDTO> cashierArrayList = getCashierDetails();
        for (int i = 0; i < cashierArrayList.size(); i++) {
            if (cashierArrayList.get(i).getCashierNic().equals(txtCashierNic.getText())
                    || cashierArrayList.get(i).getCashierContactNo().equals(txtCashierContactNo.getText())
                    || cashierArrayList.get(i).getCashierUserName().equals(txtCashierUserName.getText())) {
                if (cashierArrayList.get(i).getCashierNic().equals(txtCashierNic.getText())) {
                    new Alert(Alert.AlertType.WARNING, " Nic already Exist. Please Try Again", ButtonType.CLOSE).show();
                    addCashierBtn.setDisable(true);
                    return;
                } else if (cashierArrayList.get(i).getCashierContactNo().equals(txtSearchCashier.getText())) {
                    new Alert(Alert.AlertType.WARNING, " Contact No already Exist. Please Try Again", ButtonType.CLOSE).show();
                    addCashierBtn.setDisable(true);
                    return;
                } else if (cashierArrayList.get(i).getCashierUserName().equals(txtCashierUserName.getText())) {
                    new Alert(Alert.AlertType.WARNING, " User Name or Password is already Exist. Please Try Again", ButtonType.CLOSE).show();
                    addCashierBtn.setDisable(true);
                    return;
                } else {
                    new Alert(Alert.AlertType.WARNING, " Login credentials are already Exist. Please Try Again", ButtonType.CLOSE).show();
                    addCashierBtn.setDisable(true);
                    return;
                }
            }
        }
        if (cashierBO.saveCashier(cashierDTO)) {
            cashierDetailsObservableList.add(new CashierDetailsTm(cashierDTO.getCashierId(), cashierDTO.getCashierName(), cashierDTO.getCashierNic(), cashierDTO.getCashierContactNo(), cashierDTO.getCashierUserName(), cashierDTO.getCashierPassword()));
            tblCashierDetailsView.refresh();
            clearTextFields();
            setNotificationSuccess("Cashier Added", "Cashier is Successfully added");
            CommonFunctions.setDisableFields(allValidations);
            imgSetCashierImage.setImage(null);
            txtCashierId.setDisable(false);
            generateCashierId();
            setCashierIdToTextField();
        } else {
            setNotificationWarning("Try Again", "Adding Cashier is unsuccessful");
        }
    }

    public ArrayList<CashierDTO> getCashierDetails() throws SQLException {
        ArrayList<CashierDTO> all = cashierBO.getAll();
        return all;
    }

    public String[] encryptCashierPassword() {
        char[] chars = txtCashierPassword.getText().toCharArray();
        String[] encryptedPassword = new String[chars.length];
        int key = 2;
        int index = 0;
        for (char c : chars
        ) {
            c += key;
            encryptedPassword[index] = String.valueOf(c);
            index++;
        }
        return encryptedPassword;
    }

    public void btnUpdateCashier(ActionEvent actionEvent) throws SQLException {
        String[] encryptedUpdatedPassword = encryptCashierPassword();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < encryptedUpdatedPassword.length; i++) {
            stringBuffer.append(encryptedUpdatedPassword[i]);
        }
        String encryptedPassword = stringBuffer.toString();
        System.out.println(encryptedPassword);

        String fileLocation = null;
        if (file != null) {
            fileLocation = file.toURI().toString();
            fileLocation = fileLocation.replace("\\", "\\\\");
            System.out.println("File location - " + fileLocation);
        } else {
            fileLocation = setImageToLocation(selectedRowIndex);
        }

        CashierDTO cashierDTO = new CashierDTO(txtCashierId.getText(), txtCashierName.getText(), txtCashierNic.getText(), txtCashierContactNo.getText(), txtCashierUserName.getText(), encryptedPassword, fileLocation);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update the details of the cashier", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        int existIndex = isExist();
        if (buttonType.get().equals(ButtonType.YES)) {
            if (cashierBO.updateCashier(cashierDTO)) {
                tblCashierDetailsView.getItems().remove(existIndex);
                tblCashierDetailsView.getItems().add(new CashierDetailsTm(cashierDTO.getCashierId(), cashierDTO.getCashierName(), cashierDTO.getCashierNic(), cashierDTO.getCashierContactNo(), cashierDTO.getCashierUserName(), cashierDTO.getCashierPassword()));
                tblCashierDetailsView.refresh();
                clearTextFields();
                generateCashierId();
                setCashierIdToTextField();
                imgSetCashierImage.setImage(null);
                setNotificationSuccess("Cashier Updated", "Cashier is Successfully Updated");
            } else {
                setNotificationWarning("Try Again", "Updating Cashier is unsuccessful");
            }
        } else if (buttonType.get().equals(ButtonType.NO)) {
            new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
        }
    }

    public void btnDeleteCashier(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove cashier", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        String cashierId=txtCashierId.getText();
        int index = isExist();
        if (buttonType.get().equals(ButtonType.YES)) {
            if (selectedRowIndex != -1) {
                cashierDetailsObservableList.remove(selectedRowIndex);
                if (cashierBO.deleteCashier(cashierId)) {
                    CommonFunctions.setDisableFields(allValidations);
                    clearTextFields();
                    txtCashierId.setDisable(false);
                    generateCashierId();
                    setCashierIdToTextField();
                    imgSetCashierImage.setImage(null);
                    setNotificationSuccess("Cashier Deleted", "Cashier is Successfully Deleted");
                } else {
                    clearTextFields();
                    generateCashierId();
                    setNotificationSuccess("Cashier Deleted", "Cashier is Successfully Deleted");
                }
            } else {
                if (cashierBO.deleteCashier(cashierId)) {
                    cashierDetailsObservableList.remove(index);
                    clearTextFields();
                    txtCashierId.setDisable(false);
                    generateCashierId();
                    setCashierIdToTextField();
                    imgSetCashierImage.setImage(null);
                    setNotificationSuccess("Cashier Deleted", "Cashier is Successfully Deleted");
                } else {
                    clearTextFields();
                    generateCashierId();
                    setNotificationSuccess("Cashier Deleted", "Cashier is Successfully Deleted");
                }
            }
        } else if (buttonType.get().equals(ButtonType.NO)) {
            setNotificationWarning("Try Again", "Deleting Cashier is unsuccessful");
        }
    }

    public void setCashierDetailsToTheTable() throws SQLException {
        cashierDetailsObservableList.clear();
        ArrayList<CashierDTO> all = cashierBO.getAll();
        for (CashierDTO dto : all
        ) {
            cashierDetailsObservableList.add(new CashierDetailsTm(dto.getCashierId(), dto.getCashierName(), dto.getCashierNic(), dto.getCashierContactNo(), dto.getCashierUserName(), dto.getCashierPassword()));
        }
        tblCashierDetailsView.setItems(cashierDetailsObservableList);
    }

    public void searchCashierFromTheTable() {
        ObservableList<CashierDetailsTm> cashierDetailsObservableList = tblCashierDetailsView.getItems();
        FilteredList<CashierDetailsTm> filteredData = new FilteredList<>(cashierDetailsObservableList, p -> true);
        txtSearchCashier.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                String contactNo = String.valueOf(person.getCashierContactNo());
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getCashierId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getCashierName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (contactNo.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CashierDetailsTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCashierDetailsView.comparatorProperty());
        tblCashierDetailsView.setItems(sortedData);
    }

    public void clearTextFields() {
        CommonFunctions.clearFields(allValidations);
    }

    public void setCashierIdToTextField() {
        String id = null;
        try {
            txtCashierId.setDisable(false);
            id = generateCashierId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txtCashierId.setText(id);
    }

    public int isExist() throws SQLException {
        int count = 0;
        ObservableList<CashierDetailsTm> details = tblCashierDetailsView.getItems();
        for (CashierDetailsTm c : details
        ) {
            if (c.getCashierId().equals(txtCashierId.getText())) {
                return count;
            }
            count++;
        }
        return -1;
    }

    public void validateTextFields() {
        allValidations.put(txtCashierId, idPattern);
        allValidations.put(txtCashierName, namePattern);
        allValidations.put(txtCashierNic, nicPattern);
        allValidations.put(txtCashierContactNo, contactNoPattern);
        allValidations.put(txtCashierUserName, userNamePattern);
        allValidations.put(txtCashierPassword, passwordPattern);
    }

    public void cashierTextFieldEvent(KeyEvent keyEvent) {
        Object response = Validator.validate(allValidations, addCashierBtn);
        if (response instanceof Boolean) {
            addCashierBtn.setDisable(false);
            txtField.setDisable(false);
            btnChooseFileID.setDisable(false);
        }
        if (keyEvent.getCode() == KeyCode.TAB) {
            if (response instanceof TextField) {
                TextField txt = (TextField) response;
                txt.requestFocus();
                btnChooseFileID.setDisable(true);
            }
        }
    }

    public void btnChooseFile(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();
        System.out.println(file.toURI().toString());
        Image img = new Image(file.toURI().toString());
        imgSetCashierImage.setImage(img);
    }
}
