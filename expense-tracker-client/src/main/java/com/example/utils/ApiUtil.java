package com.example.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

/**
 * Utility class for API-related helper methods.
 * retrieve and send api requests
 */

public class ApiUtil {
    private static final String SPRINGBOOT_URL = "http://localhost:8080";
    public enum RequestMethod{POST, GET, PUT, DELETE}

    public static HttpURLConnection fetchApi(String apiPath, RequestMethod requestMethod, JsonObject jsonData) {
        try {
            // attempt to create a connection
            URL url = new URL(SPRINGBOOT_URL + apiPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set the request type
            connection.setRequestMethod(requestMethod.toString());

            // to send data to the api
            if (jsonData != null && requestMethod != RequestMethod.GET) {
                // lets the api know that we will be sending in json data 
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                // expects the repsonse body to be of json type
                connection.setRequestProperty("Accept", "application/json");

                // allows us to send data to the connected api
                connection.setDoOutput(true);

                // send json data to the server
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);

                    os.write(input, 0, input.length);
                }
            }


            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
