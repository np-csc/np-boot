package cn.np.boots.cloud.gateway.soul.springmvc;

import cn.np.boots.cloud.gateway.client.spi.springmvc.ISpringMvcGatewayRegisterProcess;
import cn.np.boots.cloud.gateway.client.spi.springmvc.SpringMvcGatewayRegisterInfo;
import cn.np.boots.common.utils.NpUtils;

import java.util.List;

public class SoulSpringMvcRegisterSPI implements ISpringMvcGatewayRegisterProcess {
    @Override
    public void process(List<SpringMvcGatewayRegisterInfo> registerInfos) {
        if(registerInfos == null) return;


    }

    public void t(SpringMvcGatewayRegisterInfo registerInfo){
        String appName = registerInfo.getAppName();
        String path = registerInfo.getPath();
        String desc = registerInfo.getDesc();
        String host = registerInfo.getHost();
        Integer port = registerInfo.getPort();
        boolean enabled = registerInfo.isEnabled();

        SoulSpringMvcRegisterDTO registerDTO = SoulSpringMvcRegisterDTO.builder()
                .appName(appName)
                .context(null)
                .ruleName(null)
                .path(path)
                .pathDesc(desc)
                .host(host)
                .port(port)
                .rpcType("http")
                .enabled(enabled).build();

        NpUtils.web().action().post();
//
//        String appName = soulSpringMvcConfig.getAppName();
//        Integer port = soulSpringMvcConfig.getPort();
//        String path = contextPath + "/**";
//        String configHost = soulSpringMvcConfig.getHost();
//        String host = ("".equals(configHost) || null == configHost) ? IpUtils.getHost() : configHost;
//        SpringMvcRegisterDTO registerDTO = SpringMvcRegisterDTO.builder()
//                .context(contextPath)
//                .host(host)
//                .port(port)
//                .appName(appName)
//                .path(path)
//                .rpcType("http")
//                .enabled(true)
//                .ruleName(path)
//                .build();
//        return OkHttpTools.getInstance().getGosn().toJson(registerDTO);
    }

}
