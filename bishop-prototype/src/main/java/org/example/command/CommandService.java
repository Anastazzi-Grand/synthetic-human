package org.example.command;

import org.example.queue.QueueFullException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class CommandService {

    private final ThreadPoolExecutor threadPoolExecutor;

    public CommandService(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }


    public void process(Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            executeImmediately(command);
        } else {
            enqueue(command);
        }
    }

    public void executeImmediately(Command command) {
        System.out.println("Выполняется команда: " + command.getDescription());
    }

    public void enqueue(Command command) {
        try {
            threadPoolExecutor.execute(new CommandTask(command));
        } catch (RejectedExecutionException e) {
            throw new QueueFullException("Очередь переполнена, команда не принята");
        }
    }
}
