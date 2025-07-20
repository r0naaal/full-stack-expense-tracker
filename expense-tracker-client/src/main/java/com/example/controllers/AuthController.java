package com.example.controllers;

import com.example.views.AuthView;
import com.example.views.DashboardView;

import javafx.scene.control.Alert;

public class AuthController {
    private AuthView view;
    
    public AuthController(AuthView view) {
        this.view = view;
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // Mode toggle handlers
        view.getLoginToggleButton().setOnAction(e -> view.switchToLoginMode());
        view.getSignupToggleButton().setOnAction(e -> view.switchToSignupMode());
        
        // Login form handlers
        view.getLoginButton().setOnAction(e -> handleLogin());
        
        // Signup form handlers
        view.getSignupButton().setOnAction(e -> handleSignup());
    }
    
    private void handleLogin() {
        String email = view.getLoginEmailField().getText();
        String password = view.getLoginPasswordField().getText();
        boolean rememberMe = view.getRememberMeCheckBox().isSelected();
        
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }
        
        // TODO: Implement actual authentication logic
        new DashboardView().show("Cafe");
    }
    
    private void handleSignup() {
        String firstName = view.getFirstNameField().getText();
        String lastName = view.getLastNameField().getText();
        String email = view.getSignupEmailField().getText();
        String password = view.getSignupPasswordField().getText();
        String confirmPassword = view.getConfirmPasswordField().getText();
        boolean acceptedTerms = view.getTermsCheckBox().isSelected();
        
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match");
            return;
        }
        
        if (!acceptedTerms) {
            showAlert("Please accept the terms and conditions");
            return;
        }
        
        // TODO: Implement actual registration logic
        showAlert("Account created successfully! (Demo)");
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}