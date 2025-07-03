package com.example.controllers;

import com.example.views.LoginView;
import com.example.views.SignUpView;

import javafx.event.EventHandler;
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
    }
}
