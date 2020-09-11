package cn.np.boots.common.utils.spring.resource;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Accessors(fluent = true)
@Component
public class NpSpringResourceUtils {
    public Resource[] getResources(String location) {
        final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
