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
        try {
            System.out.println("Выполняется задача: " + command.getDescription());
            Thread.sleep(5000); // Задержка 5 секунд
            commandMetrics.recordCommand(command.getAuthor());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
