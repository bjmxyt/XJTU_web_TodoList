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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MyTask> create(@RequestBody MyTask MyTask) {
        MyService.saveMyTask(MyTask);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/MyTasks/{id}")
                .buildAndExpand(MyTask.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MyTask> update(@PathVariable Long id, @RequestBody MyTask MyTask) {
        Optional<MyTask> updatedMyTask = MyService.update(new MyTask(id, MyTask.getcontent()));
        return ResponseEntity.of(updatedMyTask);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<MyTask> deletedMyTask = MyService.delete(id);
        if (deletedMyTask.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
