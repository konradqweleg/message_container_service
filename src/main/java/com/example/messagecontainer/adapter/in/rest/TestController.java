package com.example.messagecontainer.adapter.in.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/get")
public class TestController {

    @RequestMapping
    public String get() {
        return "Hello Worldpp";
    }
}
