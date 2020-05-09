package com.example.demo.testing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmpInitData {

    @PostConstruct
    void initData(){

    }
}
