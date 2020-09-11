package cn.np.boots.cloud.gateway.soul.springmvc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SoulSpringMvcRegisterDTO {
    private String appName;

    private String context;

    private String path;

    private String pathDesc;

    private String rpcType;

    private String host;

    private Integer port;

    private String ruleName;

    private boolean enabled;
}
