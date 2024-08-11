package com.example.demo.Controller;

import com.example.demo.Entity.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping
    public ResponseEntity<Message> message() {
        Message message = new Message("BC4M");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> add(@RequestBody Message greeting){
        return new ResponseEntity<>(greeting, HttpStatus.CREATED);
    }
}
