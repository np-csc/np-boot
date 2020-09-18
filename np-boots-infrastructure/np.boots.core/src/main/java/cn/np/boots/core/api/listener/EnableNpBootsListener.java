package cn.np.boots.core.api.listener;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(NpBootsApplicationLifecycle.class)
@Repeatable(EnableNpBootsListenerRepeatable.class)
public @interface EnableNpBootsListener {

    public static final String PROPERTY_ORDER = "order";
    public static final String PROPERTY_Name = "name";
    public static final String PROPERTY_LIFECYCLE = "lifecycle";

    int order() default 0;
    String name() default "";
    Class<? extends NpBootsLifecycleListener> lifecycle();
}
