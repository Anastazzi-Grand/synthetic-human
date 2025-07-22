package org.example.audit;

public interface AuditService {
    void audit(String methodName, Object[] args, Object result);
}
