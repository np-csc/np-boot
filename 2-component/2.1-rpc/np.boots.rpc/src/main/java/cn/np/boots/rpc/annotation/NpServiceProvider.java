package cn.np.boots.rpc.annotation;

public @interface NpServiceProvider {
    Class<?> contract();
    String[] binding() default {"spi"};
}
