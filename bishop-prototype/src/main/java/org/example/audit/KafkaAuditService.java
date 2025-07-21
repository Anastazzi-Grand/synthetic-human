package org.example.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@ConditionalOnProperty(name = "audit.mode", havingValue = "kafka")
public class KafkaAuditService implements AuditService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaAuditService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void audit(String methodName, Object[] args, Object result) {
        String message = String.format("Метод: %s, Аргументы: %s, Результат: %s", methodName, Arrays.toString(args), result);
        kafkaTemplate.send("audit-topic", message);
    }
}
