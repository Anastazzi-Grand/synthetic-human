package com.example.command;

import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

@Component
public class CommandValidator {
    public void validate(Command command) {
        if (command.getDescription() == null || command.getDescription().length() > 1000) {
            throw new CommandValidationException("Description не может быть пустой и должна быть до 1000 символов");
        }

        if (command.getPriority() == null) {
            throw new CommandValidationException("Priority обязательна");
        }

        if (command.getAuthor() == null || command.getAuthor().length() > 100) {
            throw new CommandValidationException("Author не может быть пустой и должен быть до 100 символов");
        }

        if (command.getTime() == null || !isValidISO8601(command.getTime())) {
            throw new CommandValidationException("Time должна быть в формате ISO-8601");
        }
    }

    public boolean isValidISO8601(String dateStr) {
        try {
            DateTimeFormatter.ISO_DATE_TIME.parse(dateStr);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
