package cn.np.boots.test.cloud;

import cn.np.boots.test.common.BaseTest;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class ConfigurationTest extends BaseTest {
    @Autowired
    private TestConfiguration configuration;


    @Test
    public void test() {
        System.out.println(configuration.getValue());
    }
}

@NacosPropertySource(dataId = "test2.yaml")
@Component
@Configuration
@ConfigurationProperties(prefix = "np.xx.test")
@Data
class TestConfiguration {
    private String value;
}
