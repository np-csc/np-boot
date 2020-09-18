package cn.np.boots.core.api.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NpOnBeanPostEvent {
   private Object bean;
   private String beanName;
}
