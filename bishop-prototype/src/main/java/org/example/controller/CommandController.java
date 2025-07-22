package org.example.controller;

import jakarta.validation.Valid;
import org.example.command.Command;
import org.example.command.CommandService;
import org.example.command.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<String> executeCommand(@Valid @RequestBody Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            commandService.executeImmediately(command); // ← Вызов через прокси
        } else {
            commandService.enqueue(command); // ← Вызов через прокси
        }
        return ResponseEntity.ok("Команда принята");
    }
}
