package dev.aronba.server.exception;

public class InvalidServerStateException extends RuntimeException{
    public InvalidServerStateException(String s) {
        super(s);
    }
}
