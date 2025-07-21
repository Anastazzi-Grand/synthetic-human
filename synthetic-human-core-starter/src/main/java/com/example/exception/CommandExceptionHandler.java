package com.example.exception;

import com.example.command.CommandValidationException;
import com.example.queue.QueueFullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommandExceptionHandler {

    @ExceptionHandler(CommandValidationException.class)
    public ResponseEntity<CommandErrorResponse> handleValidation(CommandValidationException ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "Unknown"
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(QueueFullException.class)
    public ResponseEntity<CommandErrorResponse> handleQueueFull(QueueFullException ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Unknown"
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommandErrorResponse> handleGeneric(Exception ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                "Внутренняя ошибка сервера",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown"
        );
        return ResponseEntity.internalServerError().body(error);
    }
}

