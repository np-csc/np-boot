package cn.np.boots.common.pattern.log;

import cn.np.boots.common.pattern.lamda.FunctionVoid;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class NpLogPattern {

    public void aroundDebug(String debugInfo, FunctionVoid execute) {

        if (log.isDebugEnabled()) {
            log.debug(debugInfo + "-beginning");
        }

        try {
            execute.execute();
        } catch (Exception e) {
            log.error(debugInfo + "-error", e);
        }

        if (log.isDebugEnabled()) {
            log.debug(debugInfo + "-ended");
        }
    }
}
