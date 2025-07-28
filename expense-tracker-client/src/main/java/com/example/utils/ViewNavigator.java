package com.example.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator {
    private static Stage mainStage;
    
    public static void setMainStage(Stage stage){
        mainStage = stage; 
    }

    // scene is the view (Login, signup, dashboard)
    public static void switchViews(Scene scene){
        if (mainStage != null) {
            mainStage.setScene(scene);
            mainStage.setFullScreen(true);
            mainStage.show();
        }
    }
}
