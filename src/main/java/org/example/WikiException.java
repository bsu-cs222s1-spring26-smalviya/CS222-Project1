package org.example;

public class WikiException extends Exception {
    public WikiException(String message) {
        super(message);
    }
    public WikiException(String message, Throwable cause) {
        super(message, cause);
    }
}
