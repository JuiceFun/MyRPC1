package com.zhang.v1.service;


import com.zhang.v1.common.User;

public interface UserService {

    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}
