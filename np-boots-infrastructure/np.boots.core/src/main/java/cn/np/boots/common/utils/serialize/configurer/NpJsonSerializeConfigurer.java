package cn.np.boots.common.utils.serialize.configurer;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.core.pattern.asserts.AssertPattern;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoBeforeBeanDefinitionConfigurer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class NpJsonSerializeConfigurer extends NpSpringAutoBeforeBeanDefinitionConfigurer {
    @Override
    public void doAction() {
        if(NpUtils.spring().bean().tryGetBeanByType(ObjectMapper.class) != null){
            log.debug("NP:com.fasterxml.jackson.databind.ObjectMapper exits,ignore mapper register");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);

        //序列化的时候序列对象的所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        NpUtils.serialize().json().objectMapper = objectMapper;

        String beanName = ObjectMapper.class.getName();
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ObjectMapper.class);
//        beanDefinitionBuilder.setLazyInit(false);
//        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
//        DefaultListableBeanFactory beanFactory = NpUtils.spring().context().getBeanFactory();
//        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        DefaultListableBeanFactory beanFactory = NpUtils.spring().context().getBeanFactory();
        beanFactory.registerSingleton(beanName,objectMapper);

        objectMapper = AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByName(beanName, ObjectMapper.class), "getOrRegisterSingleBeanByName failed");

//        ConfigurationPropertiesBindingPostProcessor configurationPropertiesBindingPostProcessor =
//                beanFactory.getBean(ConfigurationPropertiesBindingPostProcessor.class);
//        configurationPropertiesBindingPostProcessor.postProcessBeforeInitialization(objectMapper, beanName);
//
//        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AutowiredAnnotationBeanPostProcessor.class);
//        autowiredAnnotationBeanPostProcessor.postProcessBeforeInitialization(objectMapper, beanName);

        //NpUtils.spring().bean().registerSingleBeanByName(ObjectMapper.class.getName(),objectMapper);
    }
}
