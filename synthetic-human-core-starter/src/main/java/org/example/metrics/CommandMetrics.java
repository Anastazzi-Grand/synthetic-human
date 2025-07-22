package org.example.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class CommandMetrics {

    private final MeterRegistry registry;
    private final Map<String, Counter> counters = new ConcurrentHashMap<>();
    private final Gauge queueSizeGauge;

    public CommandMetrics(MeterRegistry registry, ThreadPoolExecutor threadPoolExecutor) {
        this.registry = registry;
        this.queueSizeGauge = Gauge.builder("queue.size", threadPoolExecutor, executor -> executor.getQueue().size())
                .description("Текущее количество задач в очереди")
                .register(registry);
    }

    public void recordCommand(String author) {
        if (author == null || author.isBlank()) {
            author = "unknown";
        }

        Counter counter = counters.computeIfAbsent(author, a ->
                Counter.builder("commands.by.author")
                        .description("Количество выполненных команд по авторам")
                        .tag("author", a)
                        .register(registry)
        );

        counter.increment();
    }
}