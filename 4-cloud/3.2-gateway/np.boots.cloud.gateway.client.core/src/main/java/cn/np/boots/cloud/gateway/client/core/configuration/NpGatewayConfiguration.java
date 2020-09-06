package cn.np.boots.cloud.gateway.client.core.configuration;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@NacosPropertySource(dataId = "application-gateway.yaml")
@Component
@Configuration
@ConfigurationProperties(prefix = "np.gateway")
@Data
public class NpGatewayConfiguration {
    private String url;
    private String appName;
    private String contextPrefix;
}
