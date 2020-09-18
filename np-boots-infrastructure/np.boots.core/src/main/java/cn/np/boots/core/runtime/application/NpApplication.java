package cn.np.boots.core.runtime.application;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 进程(应用)->容器->模块->组件
 *
 * SpringContext ClassLoader
 *
 * 标准模式(正常使用):同进程,同ClassLoader,同SpringContext
 * 进程启动->加载容器(使用进程S&C)->加载模块(使用容器S&C)->加载组件(使用容器S&C)
 *
 *
 * 模块隔离模式(半隔离,上下文环境不同,不允许jar版本不同):同进程,同ClassLoader,不同SpringContext(无法直接DI,但可以内反射)
 * 进程启动->加载容器(使用进程C&新建S)->加载模块(使用容器S&C)->加载组件(使用容器S&C)   ??保持S的级联关系??
 *
 *
 * 容器隔离模式(全隔离,允许jar版本不同):同进程,不同ClassLoader,不同SpringContext(无法直接DI,只能外反射)
 * 进程启动->加载容器(新建C&新建S)->加载模块(使用容器S&C)->加载组件(使用容器S&C)
 */
public class NpApplication {
    protected final ConfigurableApplicationContext rootApplicationContext;
    protected final ClassLoader rootClassLoader;

    public NpApplication(ConfigurableApplicationContext rootApplicationContext,ClassLoader rootClassLoader){
        this.rootApplicationContext = rootApplicationContext;
        this.rootClassLoader = rootClassLoader;
        this.rootApplicationContext.getC
    }
}
