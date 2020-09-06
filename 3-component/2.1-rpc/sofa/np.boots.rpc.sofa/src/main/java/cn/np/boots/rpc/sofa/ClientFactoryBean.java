package cn.np.boots.rpc.sofa;

import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ServiceClient;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import com.alipay.sofa.runtime.api.client.param.ServiceParam;

import java.util.ArrayList;
import java.util.List;

public class ClientFactoryBean implements ClientFactoryAware {

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        ServiceClient serviceClient = clientFactory.getClient(ServiceClient.class);

        ServiceParam serviceParam = new ServiceParam();
        serviceParam.setInterfaceType(ServiceInterface.class);
        serviceParam.setInstance(new ServiceImpl());

        List<BindingParam> params = new ArrayList<BindingParam>();
        BindingParam serviceBindingParam = new BoltBindingParam();

        params.add(serviceBindingParam);
        serviceParam.setBindingParams(params);

        serviceClient.service(serviceParam);
    }
}

interface ServiceInterface{

}
class ServiceImpl implements ServiceInterface{

}

