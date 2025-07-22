package org.example.command;

import org.example.annotation.WeylandWatchingYou;
import org.example.metrics.CommandMetrics;
import org.example.queue.QueueFullException;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class CommandService {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final CommandMetrics commandMetrics;

    public CommandService(ThreadPoolExecutor threadPoolExecutor, CommandMetrics commandMetrics) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.commandMetrics = commandMetrics;
    }

    @WeylandWatchingYou
    public void executeImmediately(Command command) {
        System.out.println("Выполняется команда: " + command.getDescription());
        commandMetrics.recordCommand(command.getAuthor());
    }

    @WeylandWatchingYou
    public void enqueue(Command command) {
        try {
            threadPoolExecutor.execute(new CommandTask(command, commandMetrics));
        } catch (RejectedExecutionException e) {
            throw new QueueFullException("Очередь переполнена, команда не принята");
        }
    }
}
