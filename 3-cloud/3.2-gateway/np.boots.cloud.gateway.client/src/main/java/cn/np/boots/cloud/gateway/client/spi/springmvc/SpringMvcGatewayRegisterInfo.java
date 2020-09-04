package cn.np.boots.cloud.gateway.client.spi.springmvc;

import lombok.Data;

@Data
public class SpringMvcGatewayRegisterInfo {
    private String context;
    private String path;
    private String desc;
    private boolean enabled;

    private String appName;
    private String host;
    private Integer port;
}
