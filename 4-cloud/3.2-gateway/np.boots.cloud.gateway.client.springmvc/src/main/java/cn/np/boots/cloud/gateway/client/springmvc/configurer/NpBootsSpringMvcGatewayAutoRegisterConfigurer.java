package cn.np.boots.cloud.gateway.client.springmvc.configurer;

import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterBeanInitializedConfigurer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class NpBootsSpringMvcGatewayAutoRegisterConfigurer extends NpSpringAutoAfterBeanInitializedConfigurer {

    @Override
    public void doAction(final Object bean, final String beanName) {
        Controller controller = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        RestController restController = AnnotationUtils.findAnnotation(bean.getClass(), RestController.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
        if (controller != null || restController != null || requestMapping != null) {

        }
    }
}
