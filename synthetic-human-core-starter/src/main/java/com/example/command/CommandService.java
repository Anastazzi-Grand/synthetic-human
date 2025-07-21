package com.example.command;

import com.example.annotation.WeylandWatchingYou;
import com.example.metrics.CommandMetrics;
import com.example.queue.QueueFullException;
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


    public void process(Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            executeImmediately(command);
        } else {
            enqueue(command);
        }
    }

    @WeylandWatchingYou
    public void executeImmediately(Command command) {
        System.out.println("Выполняется команда: " + command.getDescription());
        commandMetrics.recordCommand(command.getAuthor());
    }

    public void enqueue(Command command) {
        try {
            threadPoolExecutor.execute(new CommandTask(command, commandMetrics));
        } catch (RejectedExecutionException e) {
            throw new QueueFullException("Очередь переполнена, команда не принята");
        }
    }
}
