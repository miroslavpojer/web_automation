package automation.demo.driver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* *.*(..)) && this(automation.demo.driver.objects.IPage+)")
    public void anyIPageMethod() {
    }

    @Pointcut("execution(* *.*(..)) && this(automation.demo.driver.driver.DriverManager+)")
    public void anyDriverMethod() {
    }

    @Pointcut(value =
            "!execution(* automation.demo.driver.driver.DriverManager.get*(..))" +
                    "&& !execution (* automation.demo.driver.objects.PageObject.getDriverManager())" +
                    "&& !execution (* automation.demo.WebTest+.test*(..)) " +
                    "&& !execution (* *.lambda*(..))")
    public void filterMethods() {
    }

    @Before(value = "(anyIPageMethod() || anyDriverMethod()) && filterMethods()", argNames = "joinPoint")
    public void log(JoinPoint joinPoint) {
        Object processingObject = joinPoint.getThis();
        Logger logger;
        if (processingObject == null) {
            logger = LoggerFactory.getLogger(LoggingAspect.class);
        } else {
            logger = LoggerFactory.getLogger(processingObject.getClass());
        }
        logger.info("Enter: " + processMethodCallLog(joinPoint, processingObject, joinPoint.getArgs()));
    }

    private static String processMethodCallLog(JoinPoint joinPoint, Object processingObject, Object[] arguments) {
        Signature signature = joinPoint.getSignature();
        return processingObject == null ?
                "LoggingAspect" :
                processingObject.getClass().getSimpleName() + "." + signature.getName() + processParameters(arguments, signature);
    }

    static String processParameters(Object[] params, Signature signature) {
        StringBuilder argumentBuilder = new StringBuilder();
        argumentBuilder.append("(");

        for (int parameterIndex = 0; parameterIndex < params.length; parameterIndex++) {

            if (parameterIndex != 0) {
                argumentBuilder.append(", ");
            }

            Object argument = params[parameterIndex];
            String parameterName = getParameterName(signature, parameterIndex);
            argumentBuilder.append(parameterName)
                           .append(": ")
                           .append(argument.toString());
        }
        argumentBuilder.append(")");
        return argumentBuilder.toString();
    }

    private static String getParameterName(Signature signature, int index) {
        CodeSignature codeSignature = (CodeSignature) signature;
        return codeSignature.getParameterNames()[index];
    }
}
