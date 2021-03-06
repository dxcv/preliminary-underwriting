package cn.algerfan.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * created by viking on 2018/07/15
 * aop切面
 * @author algerfan
 */
@Component
@Aspect
public class AopServerAspectj {
    private Logger log = Logger.getLogger(this.getClass());

    //"execution(* com..mapper..*(..))" //mapper层中的任意方法
    //"execution(* com..controller..*.*(..))"//controller层中的任意方法
    // "execution(* com..service..*(..))"//service层中的任一方法
    @Pointcut("execution(* cn..controller..*(..))")
    public void cutPoint(){}

    @Around("cutPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("==========>"+joinPoint.getTarget().getClass()+":around~");

        return joinPoint.proceed();
    }
    @Before("cutPoint()")
    public void beforeCut(JoinPoint joinPoint){
        Class<?> clazz = joinPoint.getTarget().getClass();
        log.info(clazz+":before aop");
        //所有申明的方法（不包括继承的）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods){
            log.info("the declaredMethod of this class:"+m.getName());
        }
    }
    @AfterReturning("cutPoint()")
    public void afterCut(JoinPoint joinPoint){
        Class<?> clazz = joinPoint.getTarget().getClass();
        log.info(clazz+":after aop");
        //所有申明的方法（不包括继承的）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods){
            log.info("the declaredMethod of this class:"+m.getName());
        }
    }
}
