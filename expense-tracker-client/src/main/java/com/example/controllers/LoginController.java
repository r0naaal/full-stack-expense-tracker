package com.example.controllers;

import com.example.views.LoginView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        initialize();
    }

    private void initialize(){
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!validateUser()) return;

                String email = loginView.getUsernameField().getText();
                String password = loginView.getPasswordField().getText();

                // authenticate 

                // see if email exists in data base
        
                // if not then output user not found with this email

                // if email exists validate password 

                // if not the correct password output credentials not valid

            }
        });
    }

    private boolean validateUser() {
        
        // empty email
        if (loginView.getUsernameField().getText().isEmpty()) {
            return false;
        }

        // empty password
        if (loginView.getPasswordField().getText().isEmpty()) {
            return false;
        }

        return true;
    }
}
