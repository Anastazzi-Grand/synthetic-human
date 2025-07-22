package org.example.command;

public class CommandValidationException extends RuntimeException {
    public CommandValidationException(String message){
        super(message);
    }
}
