package com.example.todoapispring.service;

import org.springframework.stereotype.Service;

@Service("anotherTodoService")
public class AnotherTodoService implements TodoService{
    @Override
    public String doSomething() {
        return "Something from another todo service";
    }
}
