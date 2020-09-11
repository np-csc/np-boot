package cn.np.boots.common.utils.spring;

import cn.np.boots.common.utils.spring.bean.NpSpringBeanDefinitionUtils;
import cn.np.boots.common.utils.spring.bean.NpSpringBeanUtils;
import cn.np.boots.common.utils.spring.context.NpSpringApplicationContextUtils;
import cn.np.boots.common.utils.spring.resource.NpSpringResourceUtils;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class NpSpringUtils {
//    @Autowired
//    private NpSpringApplicationContextUtils context;
//    @Autowired
//    private NpSpringResourceUtils resource;
//    @Autowired
//    private NpSpringBeanUtils bean;
//
//    public NpSpringUtils(){
//
//    }
//
//
//    public NpSpringUtils(@Autowired NpSpringResourceUtils resource, @Autowired NpSpringBeanUtils bean, @Autowired NpSpringApplicationContextUtils context){
//        this.resource = resource;
//        this.bean = bean;
//        this.context = context;
//    }

    public NpSpringApplicationContextUtils context(){
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSpringApplicationContextUtils.class);
    }

    public NpSpringResourceUtils resource(){
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSpringResourceUtils.class);
    }

    public NpSpringBeanUtils bean(){
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSpringBeanUtils.class);
    }

    public NpSpringBeanDefinitionUtils beanDefinition(){
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSpringBeanDefinitionUtils.class);
    }
}
