package com.t1.springbasics.beans;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String hello() {
        return "Hello World!";
    }
}
