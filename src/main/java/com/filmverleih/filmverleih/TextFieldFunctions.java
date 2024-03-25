package com.filmverleih.filmverleih;

import javafx.scene.control.TextField;

public class TextFieldFunctions {
    /**
     * Adds input validation to ensure only numeric values are allowed in the TextField.
     * @param textField The TextField to which the input validation is added.
     */
    public static void addOnlyNumberChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            /*if (!newValue.matches("\\d?")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }*/
            if (!newValue.matches("^\\d{0,9}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Adds input validation to ensure only valid year values are allowed in the TextField.
     * @param textField The TextField to which the input validation is added.
     */
    public static void addYearChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            /*if (!newValue.matches("\\d?")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }*/
            if (!newValue.matches("^\\d{0,4}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Adds input validation to ensure only valid rating values are allowed in the TextField.
     * @param textField The TextField to which the input validation is added.
     */
    public static void addRatingChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d?\\.?\\d?$")) {
                textField.setText(oldValue);
            }
        });
    }
}
