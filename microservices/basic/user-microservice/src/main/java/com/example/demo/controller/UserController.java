package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.MockFeign;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MockFeign mockFeign;

    @PostMapping("/create")
    public void createUser(@Valid @RequestBody User user) {
    }

    @GetMapping("/mock")
    public User mock() {
        User user = restTemplate
                .exchange("url", HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
                }).getBody();
        try {
            User user1 = mockFeign.getUser("id");
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
        return user;
    }
}
