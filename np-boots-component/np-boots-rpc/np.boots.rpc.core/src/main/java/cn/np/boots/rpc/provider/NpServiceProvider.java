package cn.np.boots.rpc.provider;

public @interface NpServiceProvider {

    String address() default "";

    String[] binding() default {"spi"};

    Class<?> contract() default Void.class;

    String serviceUniqueId() default "";
}
