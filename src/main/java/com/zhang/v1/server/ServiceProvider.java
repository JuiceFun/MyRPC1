package com.zhang.v1.server;



import com.zhang.v1.register.ServiceRegister;
import com.zhang.v1.register.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个接口
     */
    private Map<String, Object> interfaceProvider;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public ServiceProvider(String host,int port){
        //这里传入的是服务端自己的IP地址和端口号
        this.host=host;
        this.port=port;
        this.serviceRegister=new ZkServiceRegister();
        this.interfaceProvider=new HashMap<>();
    }

  /*  public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }*/

    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for(Class clazz : interfaces){
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
            interfaceProvider.put(clazz.getName(),service);
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
