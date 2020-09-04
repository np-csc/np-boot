package cn.np.boots.rpc.annotation;

public @interface NpServiceConsumer {
    Class<?> contract();
    String binding() ;
}
