package com.pro.dao;

import com.pro.pojo.User;
import com.pro.utils.MyBatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {
    static Logger logger = Logger.getLogger(UserMapperTest.class);

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
        User userById = mapper.getUserById(1);
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
    public void getUserByLimit() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("startIndex",0);
        map.put("pageSize",2);
        List<User> userByLimit = mapper.getUserByLimit(map);
        for (User user : userByLimit) {
            System.out.println(user);
        }
    }

    @Test
    public void getUserByRowBounds(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RowBounds rowBounds =new RowBounds(1,2);
        List<User> userList = sqlSession.selectList("com.pro.dao.UserMapper.getUserByRowBounds", null, rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void testLog4j(){
        logger.info("enter log4j");
        logger.debug("enter log4j");
        logger.error("enter log4j");
    }

}
