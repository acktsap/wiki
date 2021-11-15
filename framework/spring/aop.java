import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class MultiTxAspect {
    private final ApplicationContext applicationContext;

    @Around("@annotation(a.b.annotation.MultiCKeyTransactional)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info("Name: {}", signature.getName());

        Class<?> targetClass = joinPoint.getTarget().getClass();
        MultiCKeyTransactional multiCKeyTransactional = targetClass.getAnnotation(MultiCKeyTransactional.class);
        Class<? extends Runnable> a = multiCKeyTransactional.callback();
        Runnable bean = applicationContext.getBean(a);

        // store args
        log.info("Run multi transactional method {} with args {}", signature.toShortString(), Arrays.toString(joinPoint.getArgs()));

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("Multi tx error", e);
            throw e;
        }
    }

}

