package cn.np.boots.common.utils.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class NpNetToolUtils {
    public static String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info("Np:NpNetToolUtils getHost error", e);
            return "0.0.0.0";
        }
    }
}
