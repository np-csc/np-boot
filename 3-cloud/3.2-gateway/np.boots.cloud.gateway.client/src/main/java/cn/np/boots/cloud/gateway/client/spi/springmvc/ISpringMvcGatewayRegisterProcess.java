package cn.np.boots.cloud.gateway.client.spi.springmvc;

import java.util.List;

public interface ISpringMvcGatewayRegisterProcess {
    void process(List<SpringMvcGatewayRegisterInfo> registerInfos);
}
