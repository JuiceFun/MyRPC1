package com.zhang.v1.loadbalance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}
