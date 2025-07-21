package CommandTests;

import com.example.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class CommandServiceTest {

    private ThreadPoolExecutor mockExecutor;
    private CommandService commandService;

    @BeforeEach
    void setUp() {
        mockExecutor = new ThreadPoolExecutor(
                1, 1,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2)
        );
        commandService = new CommandService(mockExecutor);
    }

    @Test
    void testCommonCommandAddedToQueue() throws InterruptedException {
        // Создаём пул с 1 потоком и очередью на 1 задачу
        ThreadPoolExecutor testExecutor = new ThreadPoolExecutor(
                1, 1,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
        );

        CommandService testService = new CommandService(testExecutor);

        Command command1 = new Command("Задача 1", Priority.COMMON, "Автор 1", "2025-04-05T12:00:00Z");
        Command command2 = new Command("Задача 2", Priority.COMMON, "Автор 2", "2025-04-05T12:00:00Z");

        testService.enqueue(command1);

        testService.enqueue(command2);

        assertFalse(testExecutor.getQueue().isEmpty());
    }

    @Test
    void testCriticalCommandExecutedImmediately() {
        Command command = new Command();
        command.setDescription("Проверка жизнеобеспечения");
        command.setPriority(Priority.CRITICAL);
        command.setAuthor("Командор Даллас");
        command.setTime("2025-04-05T13:00:00Z");

        // Создаем мок CommandService, чтобы проверить вызов executeImmediately
        CommandService spyService = Mockito.spy(commandService);

        spyService.process(command);

        verify(spyService).executeImmediately(command);
    }

    @Test
    void testQueueProcessesTasksInBackground() throws InterruptedException {
        Command command1 = new Command("Описание 1", Priority.COMMON, "Автор 1", "2025-04-05T12:00:00Z");
        Command command2 = new Command("Описание 2", Priority.COMMON, "Автор 2", "2025-04-05T13:00:00Z");

        commandService.enqueue(command1);
        commandService.enqueue(command2);

        Thread.sleep(1000);

        assertTrue(mockExecutor.getQueue().isEmpty());
    }
}
