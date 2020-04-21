package com.XJTU.my.store;

import com.XJTU.my.model.MyTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MyTaskStoreTest {
    @Autowired
    private MyStore taskStore;

    @AfterEach
    void tearDown() {
        taskStore.writeMyTasks(Arrays.asList(createTask(1L, "test")));
    }

    @Test
    public void shouldReadTasks() {
        List<MyTask> tasks = taskStore.readMyTasks();
        assertEquals(1, tasks.size());
        assertEquals(1L, tasks.get(0).getId());
        assertEquals("test", tasks.get(0).getcontent());
        assertEquals(LocalDateTime.of(2020, 4, 5, 0, 0), tasks.get(0).getUpdated_Time());
    }

    @Test
    public void shouldWriteTasks() {
        List<MyTask> tasks = Arrays.asList(createTask(1L, "task 1"), createTask(2L, "task 2"));

        taskStore.writeMyTasks(tasks);

        List<MyTask> tasksInStore = taskStore.readMyTasks();
        assertEquals(2, tasksInStore.size());
        assertNotNull(tasksInStore.get(1).getUpdated_Time());
        assertEquals("task 2", tasksInStore.get(1).getcontent());
    }

    private MyTask createTask(long l, String test) {
        MyTask task = new MyTask(l, test);
        task.setUpdated_Time();
        return task;
    }
}
