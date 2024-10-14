package com.yellow.dash.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "main";
    }
    @GetMapping("/login")
    public String login() {
        return "signin";
    }
    @GetMapping("/registration")
    public String registration() {
        return "signup";
    }

}
