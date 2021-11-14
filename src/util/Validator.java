package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validator {
    public static ArrayList<TextField> textFields = new ArrayList<>();
    public static TextField txtField = null;
    public static int index = 0;

    public Validator() {
    }

    public Validator(LinkedHashMap<TextField, Pattern> map) {
        addDataToTextFieldList(map);
    }

    public static void addDataToTextFieldList(LinkedHashMap<TextField, Pattern> map) {
        int count = 0;
        for (TextField txt : map.keySet()
        ) {
            textFields.add(txt);
            count++;
        }
        index = count;
        System.out.println(count);
    }

    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton button) {
        for (TextField textfield : map.keySet()
        ) {
            Pattern pattern = map.get(textfield);
            if (!pattern.matcher(textfield.getText()).matches()) {
                if (!textfield.getText().equals("")) {
                    setErrorBorderColor(textfield, button);
                    lastIndexCheck(textfield);
                }
                return textfield;
            }
            setCorrectBorderColor(textfield, button);
            setEnableTextFields(textfield);
        }
        return true;
    }

    public static void setCorrectBorderColor(TextField textfield, JFXButton button) {
        textfield.setStyle("-fx-border-color: green");
        button.setDisable(false);
    }

    public static void setErrorBorderColor(TextField textfield, JFXButton button) {
        textfield.setStyle("-fx-border-color: red");
        button.setDisable(true);
    }

    public static void lastIndexCheck(TextField textfield) {
        int checkIndex = index - 1;
        for (int i = 0; i < textFields.size(); i++) {
            if (!textFields.get(checkIndex).equals(textfield)) {
                setEnableTextFields(textfield);
                txtField.setDisable(true);
                return;
            } else if (textFields.get(checkIndex).getText().equals(textfield)) {
                textfield.setDisable(false);
                return;
            }
        }
    }

    public static void setEnableTextFields(TextField textField) {
        for (int i = 0; i < textFields.size(); i++) {
            if (textFields.get(i).equals(textField)) {
                for (int j = i + 1; j < textFields.size(); j++) {
                    txtField = textFields.get(j);
                    txtField.setDisable(false);
                    return;
                }
            }
        }
    }

}

