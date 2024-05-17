package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {

  @GetMapping("/user")
  public String user(){
    return "/user";
  }

  @GetMapping("/admin")
  public String admin(){
    return "/admin";
  }

}
