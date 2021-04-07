package ro.tacheandrei.disertatie.components.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableHeader {
    String name();
    int order() default Integer.MAX_VALUE;
}
