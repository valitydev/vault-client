package dev.vality.secret.exception;

public class SecretPathNotFoundException extends RuntimeException {
    public SecretPathNotFoundException(String message) {
        super(message);
    }
}
