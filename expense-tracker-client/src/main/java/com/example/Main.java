package com.example;

import com.example.utils.ThemeManager;
import com.example.utils.ViewNavigator;
import com.example.views.AuthView;
import com.example.views.DashboardView;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        
        stage.setTitle("Expense Tracker");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        ViewNavigator.setMainStage(stage);

        // create view instances
        DashboardView dashboardView = new DashboardView();
        AuthView authView = new AuthView();

        authView.show(ThemeManager.getInstance().getCurrentTheme());
    }

    public static void main(String[] args){
        launch(args);
    }
}