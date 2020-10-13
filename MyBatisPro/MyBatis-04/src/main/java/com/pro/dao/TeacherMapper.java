package com.pro.dao;

import com.pro.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TeacherMapper {
    Teacher getTeacher(@Param("tid") int id);
    Teacher getTeacher2(@Param("tid") int id);
}
