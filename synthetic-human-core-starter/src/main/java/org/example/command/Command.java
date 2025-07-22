package org.example.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class Command {
    @NotBlank(message = "Description не может быть пустой")
    @Size(max = 1000, message = "Description не может быть больше 1000 символов")
    private String description;
    @NotNull(message = "Priority обязательна")
    private Priority priority;
    @NotBlank(message = "Author не может быть пустым")
    @Size(max = 100, message = "Author не может быть больше 100 символов")
    private String author;
    @NotBlank(message = "Time обязательна")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z",
            message = "Time должна быть в формате ISO-8601 (например, 2025-07-20T12:00:00Z)")
    private String time;

    public Command(){}

    public Command(String description, Priority priority, String author, String time) {
        this.description = description;
        this.priority = priority;
        this.author = author;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Command{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
