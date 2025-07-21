package org.example.audit;

import org.apache.kafka.common.protocol.types.Field;

public interface AuditService {
    void audit(String methodName, Object[] args, Object result);
}
