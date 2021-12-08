package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yo")
public class HelloController {

 @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "這裡是message1");
        return "hello";
    }
 
 @GetMapping("/hello2")
    public String hello2(Model model) {
        model.addAttribute("message2", "這裡是message2");
        return "hello";
    }
}