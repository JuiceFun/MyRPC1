package com.zhang.v1.server;


import com.zhang.v1.service.BlogService;
import com.zhang.v1.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

//        Map<String, Object> serviceProvide = new HashMap<>();
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.UserService",userService);
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.BlogService",blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new ThreadPoolRPCRPCServer(serviceProvider);
        //SimpleRPCRPCServer RPCServer = new SimpleRPCRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}