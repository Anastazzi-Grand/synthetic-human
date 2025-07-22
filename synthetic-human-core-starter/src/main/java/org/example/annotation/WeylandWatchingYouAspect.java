package org.example.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.audit.AuditService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WeylandWatchingYouAspect {

    private final AuditService auditService;

    public WeylandWatchingYouAspect(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("✅ WeylandWatchingYouAspect создан! AuditService = " + auditService.getClass().getSimpleName());
    }

    @AfterReturning(
            pointcut = "@annotation(org.example.annotation.WeylandWatchingYou)",
            returning = "result"
    )
    public void logMethodExecution(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Object[] args = joinPoint.getArgs();

        auditService.audit(methodName, args, result);
    }
}
