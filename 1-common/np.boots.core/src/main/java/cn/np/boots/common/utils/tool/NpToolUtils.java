package cn.np.boots.common.utils.tool;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Accessors(fluent = true)
@Component
public class NpToolUtils {
    private NpNetToolUtils tool;

    @Autowired
    public NpToolUtils(NpNetToolUtils tool){
        this.tool = tool;
    }
}
