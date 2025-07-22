package org.example.exception;

import org.example.command.CommandValidationException;
import org.example.queue.QueueFullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommandExceptionHandler {

    @ExceptionHandler(CommandValidationException.class)
    public ResponseEntity<CommandErrorResponse> handleValidation(CommandValidationException ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "/api/commands"
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(QueueFullException.class)
    public ResponseEntity<CommandErrorResponse> handleQueueFullException(QueueFullException ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "/api/commands"
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommandErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Ошибка валидации");

        CommandErrorResponse errorResponse = new CommandErrorResponse(errorMessage, 400, "/api/commands");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommandErrorResponse> handleGeneric(Exception ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                "Внутренняя ошибка сервера",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "/api/commands"
        );
        return ResponseEntity.internalServerError().body(error);
    }
}

