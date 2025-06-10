package com.example.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

// This class is intended to handle navigation between different views in the application
// it provides methods to switch between views, manage the current view, and possibly handle view transitions.

public class ViewNavigator {
    // a stage is the window or JFrame
    private static Stage mainStage; // only getting updated within this class
    
    public static void setMainStage(Stage stage){
        mainStage = stage; 
    }

    // scene is the view (Login, signup, dashboard)
    public static void switchViews(Scene scene){
        if (mainStage != null) {
            mainStage.setScene(scene);
            mainStage.show();
        }
    }
}
