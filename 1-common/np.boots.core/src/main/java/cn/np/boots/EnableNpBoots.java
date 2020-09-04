package cn.np.boots;

import cn.np.boots.common.utils.serialize.configurer.NpJsonSerializeConfigurer;
import cn.np.boots.core.spring.auto.EnableNpAutoBeforeBeanDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableNpAutoBeforeBeanDefinition(action = NpJsonSerializeConfigurer.class)
public @interface EnableNpBoots {

}
