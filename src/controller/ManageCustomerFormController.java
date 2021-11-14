package controller;

import bo.BOFactory;
import bo.custom.ManageCustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.CustomerTm;
import util.CommonFunctions;
import util.Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.CommonFunctions.setNotificationSuccess;
import static util.CommonFunctions.setNotificationWarning;
import static util.Validator.textFields;
import static util.Validator.txtField;

public class ManageCustomerFormController {
    public AnchorPane customerDetailsPane;
    public TableView<CustomerTm> tblCustomerDetails;
    public TableColumn tblCustomerId;
    public TableColumn tblCustomerName;
    public TableColumn tblCustomerTelNo;
    public TableColumn tblCustomerAddress;
    public TextField txtSearchCustomers;
    public Label lblSetTotalRegisteredCustomers;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContactNo;
    public JFXTextField txtCustomerAddress;
    public Label lblSetLastlyAddedCustomer;
    public JFXButton addCustomerButtonId;
    LinkedHashMap<TextField, Pattern> validations = new LinkedHashMap<>();
    Pattern customerIdPattern = Pattern.compile("^(C-)[0-9]{3}$");
    Pattern customerNamePattern = Pattern.compile("^[A-z ]+$");
    Pattern customerTelNoPattern = Pattern.compile("^([0][0-9]{9})$");
    Pattern customerAddressPattern = Pattern.compile("^[A-z0-9+/, ]+[.]?$");
    int selectedIndexFromTheTable = -1;
    private ManageCustomerBO manageCustomerBO = (ManageCustomerBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.CUSTOMER);
    private final ObservableList<CustomerTm>customerObservableList= FXCollections.observableArrayList();

    public void initialize() {
        textFields.clear();
        validateTextFields();
        new Validator(validations);

        try {
            setCustomerId();
            getPreviousCustomerRegistered();
            getCountOfCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustomerTelNo.setCellValueFactory(new PropertyValueFactory<>("customerContactNo"));
        tblCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        setDataToTable();

        searchCustomersFromTheTable();
        selectColumnFromTheTableAndSetValues();
    }

    public void selectColumnFromTheTableAndSetValues() {
        tblCustomerDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            CommonFunctions.setEnableTextFields(validations);
            selectedIndexFromTheTable = (int) newValue;
            txtCustomerId.setText(customerObservableList.get((Integer) newValue).getCustomerId());
            txtCustomerName.setText(customerObservableList.get((Integer) newValue).getCustomerName());
            txtCustomerContactNo.setText(String.valueOf(customerObservableList.get((Integer) newValue).getCustomerContactNo()));
            txtCustomerAddress.setText(customerObservableList.get((Integer) newValue).getCustomerAddress());
        });
    }

    public boolean ifCustomerExists() throws SQLException {
        return manageCustomerBO.ifCustomerExists(txtCustomerId.getText());
    }

    public void setCustomerId() throws SQLException {
            txtCustomerId.setDisable(false);
            String customerId = manageCustomerBO.getCustomerId();
            System.out.println(customerId);
            txtCustomerId.setText(customerId);
    }

    public void btnAddCustomer(ActionEvent actionEvent) {
        CustomerDTO customerDTO = new CustomerDTO(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerContactNo.getText(), txtCustomerAddress.getText());
        try {
            if (ifCustomerExists()) {
                CommonFunctions.setNotificationWarning("Customer exists", "Customer id - " + customerDTO.getCustomerId() + " is exists");
            } else {
                if (manageCustomerBO.saveCustomer(customerDTO)) {
                    CommonFunctions.setNotificationSuccess("Customer Added", "Customer Added Successfully");
                    customerObservableList.add(new CustomerTm(customerDTO.getCustomerId(), customerDTO.getCustomerName(), customerDTO.getCustomerContactNo(), customerDTO.getCustomerAddress()));
                    clearTextFields();
                    setCustomerId();
                    CommonFunctions.setDisableFields(validations);
                    getCountOfCustomers();
                    getPreviousCustomerRegistered();
                } else {
                    CommonFunctions.setNotificationWarning("Try Again", "Adding Customer is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnUpdateCustomer(ActionEvent actionEvent) {
        CustomerDTO c = new CustomerDTO(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerContactNo.getText(), txtCustomerAddress.getText());
        int index=ifCustomerExists(txtCustomerId.getText());
        try {
            if (!ifCustomerExists()) {
                CommonFunctions.setNotificationWarning("Customer doesn't exists", "Customer id - " + c.getCustomerId() + " doesn't exists");
            } else {
                if (manageCustomerBO.updateCustomer(c)) {
                    setNotificationSuccess("Customer Updated", "Customer Updated Successfully");
                    customerObservableList.remove(index);
                    customerObservableList.add(new CustomerTm(c.getCustomerId(),c.getCustomerName(),c.getCustomerContactNo(),c.getCustomerAddress()));
                    clearTextFields();
                    CommonFunctions.setDisableFields(validations);
                    setCustomerId();
                    tblCustomerDetails.refresh();
                } else {
                    setNotificationWarning("Try Again", "Updating Customer is Unsuccessful");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove cashier", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            String customerId = txtCustomerId.getText();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (selectedIndexFromTheTable != -1) {
                    customerObservableList.remove(selectedIndexFromTheTable);
                    if (manageCustomerBO.deleteCustomer(customerId)) {
                        setNotificationSuccess("Customer Deleted", "Customer Deleted Successfully");
                        clearTextFields();
                        getPreviousCustomerRegistered();
                        getCountOfCustomers();
                        CommonFunctions.setDisableFields(validations);
                        setCustomerId();
                    } else {
                        setNotificationWarning("Try Again", "Deletion of Customer is Unsuccessful");
                    }
                } else {
                    if (manageCustomerBO.deleteCustomer(customerId)) {
                        customerObservableList.remove(tblCustomerDetails.getSelectionModel().getSelectedItem());
                        setNotificationSuccess("Customer Deleted", "Customer Deleted Successfully");
                        clearTextFields();
                        getPreviousCustomerRegistered();
                        getCountOfCustomers();
                        CommonFunctions.setDisableFields(validations);
                        setCustomerId();
                    } else {
                        setNotificationWarning("Try Again", "Deletion of Customer is Unsuccessful");
                    }
                }
            } else if (buttonType.get().equals(ButtonType.NO)) {
                setNotificationWarning("Try Again", "Deletion of Customer is Unsuccessful");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public int ifCustomerExists(String id){
        for (int i = 0; i <customerObservableList.size(); i++) {
            if (customerObservableList.get(i).getCustomerId().equals(id)){
                return i;
            }
        }return -1;
    }

    public void validateTextFields() {
        validations.put(txtCustomerId, customerIdPattern);
        validations.put(txtCustomerName, customerNamePattern);
        validations.put(txtCustomerContactNo, customerTelNoPattern);
        validations.put(txtCustomerAddress, customerAddressPattern);
    }

    public void validateCustomerDetails(KeyEvent keyEvent) {
        Object response = Validator.validate(validations, addCustomerButtonId);
        if (response instanceof Boolean) {
            addCustomerButtonId.setDisable(false);
            txtField.setDisable(false);
        }

        if (response instanceof TextField) {
            if (keyEvent.getCode() == KeyCode.TAB) {
                TextField txt = (TextField) response;
                txt.requestFocus();
            }
        }
    }

    public void clearTextFields() {
        CommonFunctions.clearFields(validations);
    }

    public void setDataToTable() {
        tblCustomerDetails.getItems().clear();
        try {
            ArrayList<CustomerDTO> all = manageCustomerBO.getAll();
            for (CustomerDTO c : all
            ) {
                tblCustomerDetails.getItems().add(new CustomerTm(c.getCustomerId(), c.getCustomerName(), c.getCustomerContactNo(), c.getCustomerAddress()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getPreviousCustomerRegistered() throws SQLException {
        lblSetLastlyAddedCustomer.setText(String.valueOf(manageCustomerBO.getPreviousRegisteredCustomer()));
    }

    public void getCountOfCustomers() throws SQLException {
        lblSetTotalRegisteredCustomers.setText(String.valueOf(manageCustomerBO.getCountOfCustomersRegistered()));
    }

    public void searchCustomersFromTheTable() {
        FilteredList<CustomerTm> filteredData = new FilteredList<>(customerObservableList, p -> true);

        txtSearchCustomers.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                String contactNo = String.valueOf(person.getCustomerContactNo());

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getCustomerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (contactNo.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getCustomerAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CustomerTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCustomerDetails.comparatorProperty());
        tblCustomerDetails.setItems(sortedData);
    }
}
