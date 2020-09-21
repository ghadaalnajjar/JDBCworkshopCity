package org.example.exception;

public class MySQLConnectionException extends Exception {

    private String message;

    public MySQLConnectionException (String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}