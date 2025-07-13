package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")

public class HomeController {

    @GetMapping
    public String homepage(){

        return "homePage/homePage";

}

}
