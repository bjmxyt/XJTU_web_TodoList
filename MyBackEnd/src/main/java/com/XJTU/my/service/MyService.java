package com.XJTU.my.service;

import com.XJTU.my.model.MyTask;
import com.XJTU.my.store.MyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyService {
    @Autowired
    public MyStore store;

    public List<MyTask> getAll() {
        return store.readMyTasks();
    }



    public Optional<MyTask> find(Long id) {
        return store.readMyTasks().stream().filter(MyTask -> MyTask.getId() == id).findAny();
    }

}
