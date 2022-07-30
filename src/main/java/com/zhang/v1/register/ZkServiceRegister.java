package com.zhang.v1.register;

import com.zhang.v1.loadbalance.RoundLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZkServiceRegister implements ServiceRegister{
    private CuratorFramework client;
    private static final String rootPath="MyRPC";
    public ZkServiceRegister(){
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);
        this.client = CuratorFrameworkFactory.builder()
                .connectString("192.168.135.129:2181").sessionTimeoutMs(40000).retryPolicy(retryPolicy)
                .namespace(rootPath).build();
        this.client.start();
        System.out.println("zk连接成功！");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            if((this.client.checkExists().forPath("/"+serviceName))==null){
                this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            String path="/"+serviceName+"/"+getServerAddress(serverAddress);
            System.out.println("server address"+path);
            this.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            System.out.println("注册成功");

        } catch (Exception e) {
            System.out.println("此服务已经存在");
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> list = this.client.getChildren().forPath("/" + serviceName);
            //负载均衡

            String path =new RoundLoadBalance().balance(list);
            return  parseAddress(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServerAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostName()+":"+serverAddress.getPort();
    }
    public InetSocketAddress parseAddress(String path){
        String[] sps = path.split(":");
        return new InetSocketAddress(sps[0],Integer.parseInt(sps[1]));
    }
}
