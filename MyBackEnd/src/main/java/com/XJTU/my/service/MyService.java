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

    public MyTask saveMyTask(MyTask MyTask) {
        List<MyTask> MyTasks = new ArrayList<>(store.readMyTasks());
        MyTask.setUpdated_Time();
        MyTasks.add(MyTask);
        store.writeMyTasks(MyTasks);
        return MyTask;
    }
    public Optional<MyTask> update(MyTask MyTask) {
        List<MyTask> MyTasks = new ArrayList<>(store.readMyTasks());
        Optional<MyTask> any = MyTasks.stream().filter(MyTask1 -> MyTask1.getId() == MyTask.getId()).findAny();
        if (any.isPresent()) {
            any.get().setcontent(MyTask.getcontent());
            any.get().setUpdated_Time();
            store.writeMyTasks(MyTasks);
        }
        return any;
    }

    public Optional<MyTask> delete(Long id) {
        List<MyTask> MyTasks = store.readMyTasks();
        Optional<MyTask> any = MyTasks.stream().filter(MyTask1 -> MyTask1.getId() == id).findAny();
        if (any.isPresent()) {
            store.writeMyTasks(MyTasks.stream().filter(MyTask -> MyTask.getId() != id).collect(Collectors.toList()));
            return any;
        }
        return any;
    }
}
