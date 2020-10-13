package com.pro.dao;

import com.pro.pojo.User;
import com.pro.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {
    @Test
    public void getUserList(){
        //第一步：获得sqlSession对象
        SqlSession sqlSession= MyBatisUtils.getSqlSession();;
        try{
            //getMapper
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();
            for(User user:userList){
                System.out.println(user);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭
            sqlSession.close();
        }
    }
    @Test
    public void getUserById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(3);
        System.out.println(userById);
        sqlSession.close();
    }

    @Test
    public void addUser(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user=new User(4,"bum","2000");
        List<User> userList = mapper.getUserList();
        int i = mapper.addUser(user);
        System.out.println(i);
        for(User u: userList){
            System.out.println(u);
        }
        //增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void addUserMap(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId",6);
        map.put("userName","lit");
        map.put("userPwd","7777");
        int i = mapper.addUserMap(map);
        System.out.println(i);
        sqlSession.commit();
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }

}
