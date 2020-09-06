package cn.np.boots.cloud.gateway.client.configuration;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class NpBootsGatewayConfiguration {
    private Boolean enableAutoRegister;
    private Boolean enableAutoRegisterSpringMvc;

    private Boolean springMvcServiceEnabled;
}
