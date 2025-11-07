package dev.vality.secret.exception;

public class HexDecodeException extends RuntimeException {
    public HexDecodeException(String message) {
        super("Secret must be in hex-format: " + message);
    }
}
