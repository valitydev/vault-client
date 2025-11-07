package dev.vality.adapter.common.v2.exception;

public class SecretAlreadyModifyException extends RuntimeException {

    public static final String CAS_ERROR_MESSAGE = "check-and-set parameter did not match the current version";

    public SecretAlreadyModifyException(Throwable cause) {
        super(cause);
    }
}
