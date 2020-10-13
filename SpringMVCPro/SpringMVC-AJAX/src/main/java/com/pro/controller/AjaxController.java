package com.pro.controller;

import com.pro.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
    @RequestMapping("/a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println(name);
        if("lam".equals(name)){
            response.getWriter().println("true");
        }else {
            response.getWriter().println("false");
        }
    }
    @RequestMapping("/a2")
    public List<User> a2(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("ll",1,"f"));
        userList.add(new User("rr",2,"m"));
        userList.add(new User("xx",3,"f"));
        return userList;
    }
    @RequestMapping("/a3")
    public String a3(String name,String pwd){
        String msg="";
        if(name!=null){
            if("root".equals(name)){
                msg="ok";
            }else{
                msg="not ok";
            }
        }
        if(pwd!=null){
            if("root".equals(pwd)){
                msg="ok";
            }else{
                msg="not ok";
            }
        }
        return msg;
    }

}
