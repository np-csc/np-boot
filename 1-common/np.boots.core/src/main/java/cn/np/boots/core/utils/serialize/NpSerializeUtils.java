package cn.np.boots.core.utils.serialize;

import cn.np.boots.core.utils.serialize.json.NpJsonSerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NpSerializeUtils {
    @Autowired
    public NpJsonSerializeUtils Json;
}
