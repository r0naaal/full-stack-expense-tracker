package com.example.controllers;

import com.example.utils.Utilities;
import com.example.views.LoginView;
import com.example.views.SignUpView;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class SignUpController {
    private SignUpView signUpView;
    public SignUpController(SignUpView signUpView){
        this.signUpView = signUpView;
        initialize();
    }

    private void initialize(){
        // validate email, password, names
            // try a connection with the api
        
        // switch to login view when clicked the link
        signUpView.getLoginLink().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                new LoginView().show();
            }
        });

        signUpView.getSignupButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(!validateInput()){
                    Utilities.showAlertDialog(Alert.AlertType.ERROR,"Invalid input");
                    return;
                }

                // extract the data in the fields
                String firstName = signUpView.getFirstNameField().getText();
                String lastName = signUpView.getLastNameField().getText();
                String email = signUpView.getEmailField().getText();
                String password = signUpView.getPasswordField().getText();
                

            }
            
        });
    }

    public boolean validateInput(){
        String firstName = signUpView.getFirstNameField().getText().trim();
        String lastName = signUpView.getLastNameField().getText().trim();
        String email = signUpView.getEmailField().getText().trim();
        String password = signUpView.getPasswordField().getText().trim();
        String confirmPassword = signUpView.getConfirmPasswordField().getText().trim();

        if (firstName.isEmpty()) return false;
        if (lastName.isEmpty()) return false;

        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        //            either -1 or 0 - at least one ch in btwn - at least two ch after '.'
        if(email.isEmpty() || at < 1 || dot < at + 2 || dot + 2 >= email.length()) return false;

        if (password.isEmpty()) return false;
        if (confirmPassword.isEmpty()) return false;
        if(!password.equals(confirmPassword)) return false;

        return true;
    }
}
