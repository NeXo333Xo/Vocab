package com.thowl.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.thowl.vocabulary.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntityController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("") 
    public String Index() {
        log.info("Entering showIndex");
        return "index";
    }

    @GetMapping("/login") 
    public String loginPage() {
        log.info("Entering login");
        return "login";
    }
}
