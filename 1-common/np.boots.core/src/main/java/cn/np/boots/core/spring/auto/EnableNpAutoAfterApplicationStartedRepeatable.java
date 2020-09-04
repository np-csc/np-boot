package cn.np.boots.core.spring.auto;

import cn.np.boots.core.spring.auto.core.NpSpringAutoRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(NpSpringAutoRegistrar.class)
public @interface EnableNpAutoAfterApplicationStartedRepeatable {

    EnableNpAutoAfterApplicationStarted[] value();
}
