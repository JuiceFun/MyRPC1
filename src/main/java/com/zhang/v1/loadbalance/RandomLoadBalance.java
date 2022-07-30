package com.zhang.v1.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance{
    @Override
    public String balance(List<String> addressList) {
        int i = new Random().nextInt(addressList.size());
        System.out.println("负载均衡选择了第"+i+"服务器");
        return addressList.get(i);
    }
}
