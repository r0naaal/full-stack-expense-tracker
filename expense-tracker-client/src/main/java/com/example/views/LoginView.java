package com.example.views;

import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {
    private Label welcomebackLabel = new Label("Welcome Back!"); 
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Don't have an account?");
    
    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainerBox = new VBox();
        mainContainerBox.getStyleClass().add("main-background");

        welcomebackLabel.getStyleClass().addAll("text-black", "header", "font-lilita");

        VBox loginFormBox = createLoginFormBox();

        mainContainerBox.getChildren().addAll(welcomebackLabel, loginFormBox);
        return new Scene(mainContainerBox, Utilities.APP_WIDTH, Utilities.APP_HEIGTH); 
    }

    private VBox createLoginFormBox(){
        VBox loginFormBox = new VBox();
        usernameField.getStyleClass().addAll("rounded-border", "text-black", "text-size-lg", "field-background", "lilita one", "text-weight-700");
        passwordField.getStyleClass().addAll("rounded-border", "text-black", "text-size-lg", "field-background");
        loginButton.getStyleClass().addAll("bg-black", "text-size-lg", "text-white", "text-weight-700", "rounded-border");
        signupLabel.getStyleClass().addAll("text-size-md", "text-white", "text-underline", "link-text");

        loginFormBox.getChildren().addAll(usernameField, passwordField, loginButton, signupLabel);
        return loginFormBox;
    }
}
