package com.app.customer.exception;


import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    public static Map<String, String> createErrorResponse(String errorMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("message", errorMessage);
        return response;
    }
}