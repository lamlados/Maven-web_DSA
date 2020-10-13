package com.pro.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取前端参数
        String method=req.getParameter("method");
        if(method.equals("add")){
            req.getSession().setAttribute("msg","add...");
        }
        if(method.equals("delete")){
            req.getSession().setAttribute("msg","delete...");
        }
        //2.调用业务层
        //3.视图转发或重定向
        req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req,resp);//转发
        //resp.sendRedirect(); //重定向
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
