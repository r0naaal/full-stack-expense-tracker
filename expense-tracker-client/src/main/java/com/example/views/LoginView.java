package com.example.views;

import com.example.controllers.LoginController;
import com.example.utils.Utilities;
import com.example.utils.ViewNavigator;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class LoginView {
    private Label welcomebackLabel = new Label("Welcome Back!"); 
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Don't have an account?");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

        new LoginController(this); // passing instance of this view
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainerBox = new VBox();
        mainContainerBox.getStyleClass().add("main-background");
        mainContainerBox.setAlignment(Pos.CENTER);
        welcomebackLabel.getStyleClass().addAll("text-black", "header", "font-lilita");
        welcomebackLabel.setMaxWidth(250);

        VBox loginFormBox = createLoginFormBox();

        mainContainerBox.getChildren().addAll(loginFormBox);
        return new Scene(mainContainerBox, Utilities.APP_WIDTH, Utilities.APP_HEIGTH); 
    }

    private VBox createLoginFormBox(){
        VBox loginFormBox = new VBox(20);
        loginFormBox.setAlignment(Pos.CENTER);

        loginFormBox.getStyleClass().addAll("bg-main-card");
        loginFormBox.setMaxWidth(400);

        ImageView logoView = new ImageView(new Image(getClass().getResource("/images/logo.png").toExternalForm()));
        logoView.setFitWidth(60); 
        logoView.setPreserveRatio(true);

        usernameField.getStyleClass().addAll("rounded-border", "text-black", "text-size-md", "field-background", "lilita one", "text-weight-700");
        usernameField.setPromptText("Enter Username");
        usernameField.setMaxWidth(240);

        passwordField.getStyleClass().addAll("rounded-border", "text-black", "text-size-md", "field-background", "lilita one", "text-weight-700");
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(240);

        loginButton.getStyleClass().addAll("bg-black", "text-size-me", "text-white", "rounded-border", "lilita one", "text-weight-700");
        loginButton.setMaxWidth(100);

        signupLabel.getStyleClass().addAll("text-size-sm", "text-black", "text-underline", "link-text");

        loginFormBox.getChildren().addAll(
            welcomebackLabel, 
            logoView,
            usernameField, 
            passwordField, 
            loginButton, 
            signupLabel
        );
        return loginFormBox;
    }

    public Label getWelcomebackLabel() { return welcomebackLabel; }
    public void setWelcomebackLabel(Label welcomebackLabel) { this.welcomebackLabel = welcomebackLabel; }

    public TextField getUsernameField() { return usernameField; }
    public void setUsernameField(TextField usernameField) { this.usernameField = usernameField; }

    public PasswordField getPasswordField() { return passwordField; }
    public void setPasswordField(PasswordField passwordField) { this.passwordField = passwordField; }

    public Button getLoginButton() { return loginButton; }
    public void setLoginButton(Button loginButton) { this.loginButton = loginButton; }

    public Label getSignupLabel() { return signupLabel; }
    public void setSignupLabel(Label signupLabel) { this.signupLabel = signupLabel; }


}
