package com.example.views;

import com.example.controllers.LoginController;
import com.example.utils.BrandingPanelUtil;
import com.example.utils.ViewNavigator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SignUpView {
    private Label titleLabel = new Label("Start Your Journey");
    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField confirmPasswordField = new PasswordField();
    private CheckBox termsCheckBox = new CheckBox();
    private Button signupButton = new Button("Create My Account");
    private Label loginLabel = new Label("Already have an account?");
    private Hyperlink loginLink = new Hyperlink("Sign in");

    private Button googleButton = new Button("Google");
    private Button githubButton = new Button("Github");

    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/signupStyle.css").toExternalForm());

        ViewNavigator.switchViews(scene);
    }

    private void createScene(){

    }

    private VBox createBrandingPanel(){
        return BrandingPanelUtil.createBrandingPanel();
    }
}
