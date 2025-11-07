package dev.vality.secret.exception;

public class SecretsNotFoundException extends RuntimeException {
    public SecretsNotFoundException(String message) {
        super(message);
    }
}
