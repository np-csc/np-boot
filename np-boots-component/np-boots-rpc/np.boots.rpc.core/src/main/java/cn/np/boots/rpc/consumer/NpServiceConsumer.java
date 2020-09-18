package cn.np.boots.rpc.consumer;

public @interface NpServiceConsumer {
    String address() default "";
    String binding() ;
    Class<?> contract();
}
