package com.example.command;

import com.example.annotation.WeylandWatchingYou;

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
