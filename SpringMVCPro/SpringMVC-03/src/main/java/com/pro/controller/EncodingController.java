package com.pro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EncodingController {
    @RequestMapping("/t/e")
    public String test(String name, Model model){
        System.out.println(name);
        model.addAttribute("msg",name);
        return "test";
    }
}
