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
            // save current state
            boolean wasMaximized = mainStage.isMaximized();
            double width = mainStage.getWidth();
            double height = mainStage.getHeight();

            mainStage.setScene(scene);

            // restore maximized state
            mainStage.setMaximized(wasMaximized);

            // if not maximized, restore size
            if (!wasMaximized) {
                mainStage.setWidth(width);
                mainStage.setHeight(height);
            }

            mainStage.show();
        }
    }
}
