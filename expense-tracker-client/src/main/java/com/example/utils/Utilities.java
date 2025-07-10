package com.example.utils;

import javafx.scene.control.Alert;

public class Utilities {
    public static final int APP_WIDTH = 1000;
    public static final int APP_HEIGTH = 800;

    public static void showAlertDialog(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
