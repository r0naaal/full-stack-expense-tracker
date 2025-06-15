package com.example.utils;

import java.net.HttpURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Utility class for API-related helper methods.
 * retrieve and send api requests
 */

public class ApiUtil {
    private static final String SPRINGBOOT_URL = "http://localhost:8080";
    public enum RequestMethod{POST, GET, PUT, DELETE}

    public static HttpURLConnection fetchApi(String apiPath, RequestMethod requestMethod, JsonObject jsonObject) {
        
    }
}
