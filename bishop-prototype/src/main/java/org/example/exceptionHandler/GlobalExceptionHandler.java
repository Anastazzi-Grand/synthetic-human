package org.example.exceptionHandler;

import org.example.exception.CommandErrorResponse;
import org.example.queue.QueueFullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(QueueFullException.class)
    public ResponseEntity<CommandErrorResponse> handleQueueFullException(QueueFullException ex) {
        CommandErrorResponse error = new CommandErrorResponse(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "/api/commands"
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
