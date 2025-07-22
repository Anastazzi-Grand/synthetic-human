package org.example.exception;

import java.time.LocalDateTime;

public class CommandErrorResponse {
    private final String message;
    private final int status;
    private final String timestamp;
    private final String path;

    public CommandErrorResponse(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString(); // создаётся автоматически
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
