package com.filmverleih.filmverleih;

import javafx.scene.control.TextField;

public class TextFieldFunctions {
    /**
     * Adds input validation to ensure only numeric values are allowed in the TextField.
     * @param textField The TextField to which the input validation is added.
     */
    public static void addOnlyNumberChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{0,9}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Adds a duration checker to the specified TextField.
     *
     * @param textField The TextField to which the duration checker will be added.
     */
    public static void addDurationChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{0,3}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Adds an FSK (Freiwillige Selbstkontrolle) checker to the specified TextField.
     *
     * @param textField The TextField to which the FSK checker will be added.
     */
    public static void addFskChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{0,2}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Adds a no comma checker to the specified TextField.
     *
     * @param textField The TextField to which the no comma checker will be added.
     */
    public static void addNoCommaChecker(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("^.*,+.*$")) {
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
