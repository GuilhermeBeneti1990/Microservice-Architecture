package com.beneti.productapi.config.response;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

    public SuccessResponse() {
    }

    public SuccessResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private Integer status;
    private String message;

    public static SuccessResponse create(String message) {
        var response = new SuccessResponse(HttpStatus.OK.value(), message);
        return response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
