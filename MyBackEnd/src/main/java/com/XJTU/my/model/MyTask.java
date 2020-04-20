package com.XJTU.my.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyTask {
    private long id;
    private String content;

    public MyTask() {
    }

    public MyTask(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }
}
