package com.example.todoapispring.service;

import org.springframework.stereotype.Service;

@Service("fakeTodoService")
public class FakeTodoService implements TodoService{

    public String doSomething() {
        return "Something";
    }

}
