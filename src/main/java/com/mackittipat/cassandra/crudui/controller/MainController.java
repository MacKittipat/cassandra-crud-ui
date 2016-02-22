package com.mackittipat.cassandra.crudui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "/index")
    public String index() {
        return "Hello";
    }
}
