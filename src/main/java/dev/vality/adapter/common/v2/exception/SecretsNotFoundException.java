package dev.vality.adapter.common.v2.exception;

public class SecretsNotFoundException extends RuntimeException {
    public SecretsNotFoundException(String message) {
        super(message);
    }
}
