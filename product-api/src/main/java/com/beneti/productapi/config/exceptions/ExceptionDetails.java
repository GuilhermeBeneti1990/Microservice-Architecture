package com.beneti.productapi.config.exceptions;

public class ExceptionDetails {

    public ExceptionDetails() {
    }

    public ExceptionDetails(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private Integer status;
    private String message;

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
