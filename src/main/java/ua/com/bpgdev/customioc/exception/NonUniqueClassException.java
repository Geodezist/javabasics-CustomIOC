package ua.com.bpgdev.customioc.exception;

public class NonUniqueClassException extends RuntimeException {
    public NonUniqueClassException() {
        super();
    }

    public NonUniqueClassException(String message) {
        super(message);
    }

    public NonUniqueClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
