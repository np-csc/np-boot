package cn.np.boots.rpc;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NacosPropertySource(dataId = "application-rpc.yaml")
public @interface EnableNpBootsRpc {

}
