package cn.np.boots.core.runtime.lifecycle.registrar;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.common.pattern.asserts.AssertPattern;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoBeforeBeanDefinitionConfigurer;
import cn.np.boots.core.spring.auto.core.NpSpringAutoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

@Slf4j
public class NpSpringBeforeBeanDefinitionRegistrar {
   public static void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator){
       if (log.isDebugEnabled()) {
           log.debug("Np:NpSpringBeforeBeanDefinitionAutoRegistrar begin");
       }

       List<Class<? extends NpSpringAutoBeforeBeanDefinitionConfigurer>> beforeBeanDefinitionActions = NpSpringAutoContext.registerBeforeBeanDefinitionAction();
       beforeBeanDefinitionActions.forEach(aClass -> {
           NpSpringAutoBeforeBeanDefinitionConfigurer autoBeforeBeanDefinitionAction = AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByType(aClass), "{} load failed", aClass.toString());
//           MergedAnnotation<?> mergedAnnotation = autoBeforeBeanDefinitionAction.getAnnotation();
//            Class<?>[] dependOnClasses = mergedAnnotation.getClassArray(NpSpringConstants.PROPERTY_DEPEND_ON);
//            if(dependOnClasses != null && dependOnClasses.length > 0){
//                for (Class<?> clazz: dependOnClasses) {
//                    NpUtils.spring().bean().
//                }
//            }
           autoBeforeBeanDefinitionAction.doAction();
       });

       if (log.isDebugEnabled()) {
           log.debug("Np:NpSpringBeforeBeanDefinitionAutoRegistrar end");
       }
   }
}
