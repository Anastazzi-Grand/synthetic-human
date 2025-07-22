package org.example.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class QueueConfig {

    @Bean
    public ThreadPoolExecutor commandExecutor() {
        return new ThreadPoolExecutor(
                2, 4, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10)
        );
    }
}
