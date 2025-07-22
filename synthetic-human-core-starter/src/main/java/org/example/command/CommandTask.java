package org.example.command;

import org.example.annotation.WeylandWatchingYou;
import org.example.metrics.CommandMetrics;

public class CommandTask implements Runnable {
    private final Command command;
    private final CommandMetrics commandMetrics;


    public CommandTask(Command command, CommandMetrics commandMetrics) {
        this.command = command;
        this.commandMetrics = commandMetrics;
    }

    @Override
    @WeylandWatchingYou
    public void run() {
        System.out.println("Выполняется задача: " + command.getDescription());
        commandMetrics.recordCommand(command.getAuthor());
    }
}
