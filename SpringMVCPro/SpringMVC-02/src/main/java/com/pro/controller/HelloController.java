package com.pro.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        //封装模型放入mv
        mv.addObject("msg","HelloSpringMVC");
        //封装要跳转的视图
        mv.setViewName("hello");
        return mv;
    }
}
