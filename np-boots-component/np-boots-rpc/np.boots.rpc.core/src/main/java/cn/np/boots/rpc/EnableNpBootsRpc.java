package cn.np.boots.rpc;

import cn.np.boots.core.api.listener.EnableNpBootsListener;
import cn.np.boots.rpc.core.register.NpRpcRegister;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NacosPropertySource(dataId = "application-rpc-2.yaml")
@EnableNpBootsListener(lifecycle = NpRpcRegister.class)
public @interface EnableNpBootsRpc {
    @AliasFor(value = "dataId", annotation = NacosPropertySource.class)
    String config();
}
