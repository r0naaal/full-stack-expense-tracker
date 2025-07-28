package com.example.utils;

import javafx.scene.control.Alert;

public class Utilities {
    public static void showAlertDialog(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
