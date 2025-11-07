package dev.vality.adapter.common.v2.exception;

public class SecretNotFoundException extends RuntimeException {
    public SecretNotFoundException(String message) {
        super(message);
    }
}
