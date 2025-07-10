package com.example.utils;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;

public class SqlUtil {

    // GET    
    public static boolean getUserByEmail(String email){
        // authenticate credentials
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                "/api/v1/user?email=" + email,
                ApiUtil.RequestMethod.GET,null
            );

            if(connection.getResponseCode() != 500){
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }

        return true;
    }

    // POST
    public static boolean postLoginUser(String email, String password) {
        
        // authenticate credentials
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                "/api/v1/user/login?email=" + email + "&password=" + password,
                ApiUtil.RequestMethod.POST,null
            );

            if(connection.getResponseCode() != 200){
                // System.out.println("Failed to authenticate");
                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed to authenticate...");
            } else {
                // System.out.println("Login Succesful");
                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Login Succesful!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }

        return true;
    }

    public static boolean postCreateUser(JsonObject userData){
        // authenticate credentials
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                "/api/v1/user",
                ApiUtil.RequestMethod.POST,
                userData
            );
            if(connection.getResponseCode() != 200){
                return false;
                // System.out.println("Failed to authenticate");
                // Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed to authenticate.");
            } 

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }

        return true; // the account was succesfully created and stored into db
    }
    
}
