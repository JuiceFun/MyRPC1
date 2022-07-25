package com.zhang.v1.client;



import com.zhang.v1.common.Blog;
import com.zhang.v1.common.User;
import com.zhang.v1.service.BlogService;
import com.zhang.v1.service.UserService;

public class RPCClient {
    public static void main(String[] args) {

        RPCClientProxy rpcClientProxy = new RPCClientProxy("192.168.10.44", 8899);
        UserService userService = rpcClientProxy.getProxy(UserService.class);

        User userByUserId = userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);

        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
