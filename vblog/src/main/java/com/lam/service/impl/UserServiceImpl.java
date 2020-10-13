package com.lam.service.impl;

import com.lam.entity.User;
import com.lam.mapper.UserMapper;
import com.lam.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lam
 * @since 2020-10-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
