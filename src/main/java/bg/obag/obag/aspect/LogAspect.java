package bg.obag.obag.aspect;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* bg.obag.obag.web.ProductController.getProductPage(..))")
    public void trackProductsView() {}

    @After("trackProductsView()")
    public void afterAdvice(JoinPoint joinPoint) throws ProductNotFoundException {
        Long productId = (Long) joinPoint.getArgs()[0];
        String action = joinPoint.getSignature().getName();

        logService.addLog(productId, action);
    }
}
