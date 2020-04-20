package com.XJTU.my.controller;

import com.XJTU.my.model.MyTask;
import com.XJTU.my.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/myapi/mytasks" )
public class MyController {
    @Autowired
    public MyService MyService;

    @GetMapping(produces = "application/json")
    public List<MyTask> list() {
        return MyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyTask> find(@PathVariable Long id) {
        return ResponseEntity.of(MyService.find(id));
    }
}
