package com.pro.dao;

import com.pro.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserList();
    User getUserById(int id);
    int addUser(User user);
    int addUserMap(Map<String,Object> map);
}
