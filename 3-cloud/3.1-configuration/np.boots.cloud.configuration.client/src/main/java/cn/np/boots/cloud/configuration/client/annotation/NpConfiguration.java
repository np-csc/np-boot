package cn.np.boots.cloud.configuration.client.annotation;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NacosPropertySource(dataId = "test.yaml")
public @interface NpConfiguration {

    @AliasFor(annotation = NacosPropertySource.class,value = "dataId")
    String dataId();
}
