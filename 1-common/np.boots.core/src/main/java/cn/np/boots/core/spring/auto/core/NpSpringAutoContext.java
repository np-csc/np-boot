package cn.np.boots.core.spring.auto.core;

import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterApplicationStartedConfigurer;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterBeanInitializedConfigurer;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoBeforeBeanDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;

public class NpSpringAutoContext {
    private static List<Class<? extends NpSpringAutoBeforeBeanDefinitionConfigurer>> beforeBeanDefinitionActions = new ArrayList<>();
    private static List<Class<? extends NpSpringAutoAfterBeanInitializedConfigurer>> afterBeanInitialized = new ArrayList<>();
    private static List<Class<? extends NpSpringAutoAfterApplicationStartedConfigurer>> afterApplicationStarted = new ArrayList<>();

    public static List<Class<? extends NpSpringAutoBeforeBeanDefinitionConfigurer>> registerBeforeBeanDefinitionAction(){
        return beforeBeanDefinitionActions;
    }
    public static void registerBeforeBeanDefinitionAction(Class<? extends NpSpringAutoBeforeBeanDefinitionConfigurer> actionClass){
        if(!beforeBeanDefinitionActions.contains(actionClass)){
            beforeBeanDefinitionActions.add(actionClass);
        }
    }

    public static void afterBeanInitialized(Class<? extends NpSpringAutoAfterBeanInitializedConfigurer> actionClass){
        if(!afterBeanInitialized.contains(actionClass)){
            afterBeanInitialized.add(actionClass);
        }
    }

    public static void afterApplicationStarted(Class<? extends NpSpringAutoAfterApplicationStartedConfigurer> actionClass){
        if(!afterApplicationStarted.contains(actionClass)){
            afterApplicationStarted.add(actionClass);
        }
    }

    public static List<Class<? extends NpSpringAutoAfterApplicationStartedConfigurer>> afterApplicationStarted(){
        return afterApplicationStarted;
    }
}
