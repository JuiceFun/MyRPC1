package com.zhang.v1.loadbalance;

import java.util.List;

public class RoundLoadBalance implements LoadBalance{
    private int round=-1;
    @Override
    public String balance(List<String> addressList) {
        round++;
        int i=round%addressList.size();
        System.out.println("负载均衡选择了第"+i+"个服务器");
        return addressList.get(i);
    }
}
