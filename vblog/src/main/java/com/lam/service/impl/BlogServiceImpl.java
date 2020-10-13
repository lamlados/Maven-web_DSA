package com.lam.service.impl;

import com.lam.entity.Blog;
import com.lam.mapper.BlogMapper;
import com.lam.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
