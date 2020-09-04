package cn.np.boots.common.utils.web;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Accessors(fluent = true)
@Component
public class NpWebUtils {
    private NpWebActionUtils action;

    @Autowired
    public NpWebUtils(NpWebActionUtils action){
        this.action = action;
    }
}
