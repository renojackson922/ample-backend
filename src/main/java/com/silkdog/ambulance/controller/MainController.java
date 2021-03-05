package com.silkdog.ambulance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("")
    public String index(Model model){

        model.addAttribute("test", "test");

        return "/main/index";
    }
}
