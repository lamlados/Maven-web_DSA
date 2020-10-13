package com.pro.dao;

import com.pro.pojo.Student;
import com.pro.pojo.Teacher;
import com.pro.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MapperTest {
    @Test
    public void getTeacher(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
    @Test
    public void getStudent(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudent2();
        for (Student student : students) {
            System.out.println(student);
        }
        sqlSession.close();
    }
}
