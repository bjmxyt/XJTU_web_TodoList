package com.XJTU.my.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyTask {
    private long id;
    private String content;
    private LocalDateTime Updated_Time;

    public MyTask() {
    }

    public MyTask(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getcontent() {
        return content;
    }

//    public LocalDateTime getUpdated_Time() {
//        return Updated_Time;
//    }

    public void setUpdated_Time() {
        this.Updated_Time = LocalDateTime.now();
    }

    public void setcontent(String content) {
        this.content = content;
    }
}
