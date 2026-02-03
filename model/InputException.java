package model;

// Custom exception for invalid input
public class InputException extends Exception {
    public InputException(String message) {
        super(message);
    }
}