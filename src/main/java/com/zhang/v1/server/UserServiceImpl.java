package com.zhang.v1.server;


import com.zhang.v1.common.User;
import com.zhang.v1.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {

        User user = User.builder().id(id).userName("zhang").sex(true).build();
        System.out.println("客户端查询了"+id+"用户");
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }
}
