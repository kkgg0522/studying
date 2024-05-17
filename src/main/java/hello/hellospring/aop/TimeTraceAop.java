package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component// or Bean 등록 (후자가 좋음)
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")   //공통 관시 사항
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        System.out.println("Start: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long timeMs = endTime - startTime;

            System.out.println("END: " + joinPoint.toString() +" "+timeMs + "ms");
        }
    }
}
