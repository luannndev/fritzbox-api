package dev.luan.fritzbox.exceptions;

public class FritzboxException extends RuntimeException {

    public FritzboxException(String message, Throwable cause) {
        super(message, cause);
    }
}