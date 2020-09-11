package cn.np.boots.common.utils.type;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Accessors(fluent = true)
@Component
public class NpTypeUtils {

    private final NpStringUtils string;

    @Autowired
    public NpTypeUtils(NpStringUtils string){
        this.string = string;
    }
}
