package com.example.todoapispring.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.todoapispring.Todo;
import com.example.todoapispring.customAnnotation.TimeMonitor;
import com.example.todoapispring.service.TodoService;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private TodoService todoService; // anotherTodoService

    private TodoService todoservice2; // fakeTodoService

    private static List<Todo> todoList;
    // Error message when the todo is not found
    private static final String TODO_NOT_FOUND = "Todo not found";

    public TodoController(
            @Qualifier("anotherTodoService") TodoService todoService,
            @Qualifier("fakeTodoService")  TodoService todoservice2) {

        this.todoService = todoService;
        this.todoservice2 = todoservice2;
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
    }


    @GetMapping
    @TimeMonitor
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(required = false) Boolean isCompleted) throws InterruptedException {
        Thread.sleep(1000);
        if(isCompleted != null) {
            List<Todo> tmpTodoList = new ArrayList<>();
            for(Todo todo : todoList) {
                if(todo.isCompleted() == isCompleted) {
                    tmpTodoList.add(todo);
                }
            }
            return ResponseEntity.ok(tmpTodoList);
        }

        System.out.println("Incoming query params: " + isCompleted + " " + this.todoService.doSomething());
        System.out.println("Incoming query params: " + isCompleted + " " + this.todoservice2.doSomething());
        return ResponseEntity.ok(todoList);
    }

    @PostMapping
    @TimeMonitor
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {

        /**
         * we can use this annotation to set the status code @ResponseStatus(HttpStatus.CREATED)
         *
         */
        todoList.add(newTodo);
        System.out.println(newTodo.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }


    @GetMapping("/{todoId}")
    @TimeMonitor
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                return ResponseEntity.ok(todo);
            }
        }
        // HW: Along with 404 status code, try to send a json {message: Todo not found}
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }
}
