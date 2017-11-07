package com.clivial.web.refactor.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liquan on 2017/11/6.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello,my first refator of casshman web test!";
    }
}
