package org.example.command;

import org.example.annotation.WeylandWatchingYou;
import org.springframework.stereotype.Component;

public class CommandTask implements Runnable {
    private final Command command;

    public CommandTask(Command command) {
        this.command = command;
    }

    @Override
    @WeylandWatchingYou
    public void run() {
        System.out.println("Выполняется задача: " + command.getDescription());
    }
}
