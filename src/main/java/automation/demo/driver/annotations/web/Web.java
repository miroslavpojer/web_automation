package automation.demo.driver.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Web {
    String id() default "";
    String linkText() default "";
    String partialLinkText() default "";
    String name() default "";
    String tagName() default "";
    String xpath() default "";
    String className() default "";
    String cssSelector() default "";

    int timeout() default -1;
}
