package org.example.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@ConditionalOnProperty(name = "audit.mode", havingValue = "console", matchIfMissing = true)
public class ConsoleAuditService implements AuditService{
    @Override
    public void audit(String methodName, Object[] args, Object result) {
        System.out.println("Метод: " + methodName);
        System.out.println("Аргументы: " + Arrays.toString(args));
        System.out.println("Результат: " + result);
    }
}
