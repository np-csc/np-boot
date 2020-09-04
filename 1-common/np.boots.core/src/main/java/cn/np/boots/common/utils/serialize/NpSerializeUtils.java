package cn.np.boots.common.utils.serialize;

import cn.np.boots.common.utils.serialize.json.NpJsonSerializeUtils;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Accessors(fluent = true)
@Component
public class NpSerializeUtils {

    private final NpJsonSerializeUtils json;

    @Autowired
    public NpSerializeUtils(NpJsonSerializeUtils json){
        this.json = json;
    }
}
