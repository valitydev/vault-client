package dev.vality.adapter.common.v2.exception;

public class HexDecodeException extends RuntimeException {
    public HexDecodeException(String message) {
        super("Secret must be in hex-format: " + message);
    }
}
