package com.example;

import com.example.utils.ViewNavigator;
import com.example.views.LoginView;
import com.example.views.SignUpView;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        
        stage.setTitle("Expense Tracker");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        ViewNavigator.setMainStage(stage);
        new SignUpView().show();
    }

    public static void main(String[] args){
        launch(args);
    }
}