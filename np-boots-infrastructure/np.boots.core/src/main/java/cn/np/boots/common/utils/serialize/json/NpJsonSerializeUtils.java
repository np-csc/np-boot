package cn.np.boots.common.utils.serialize.json;

import cn.np.boots.exception.NpInvalidateValueRuntimeException;
import cn.np.boots.exception.NpRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NpJsonSerializeUtils {
    public NpJsonSerializeUtils(){}

    @Autowired
    public ObjectMapper objectMapper;

    public String serialize(Object data){
        try {
            return objectMapper.writeValueAsString(data);
        }catch (Exception e){
            throw new NpRuntimeException(e,"NP:np-json serialize failed");
        }
    }
}
