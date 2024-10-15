package dev.luan.fritzbox.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Invalid username or password! Couldn't login.");
    }
}
