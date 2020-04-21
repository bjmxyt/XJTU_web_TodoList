package com.XJTU.my.service;

import com.XJTU.my.model.MyTask;
import com.XJTU.my.store.MyStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MyTaskServiceTest {
    @Mock
    private MyStore taskStore;

    @InjectMocks
    private MyService MyTaskService = new MyService();

    private ArrayList<MyTask> tasks;

    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
    }

    @Test
    public void shouldSaveTask() {
        when(taskStore.readMyTasks()).thenReturn(tasks);

        MyTask savedTask = MyTaskService.saveMyTask(new MyTask(1L, "newTask"));

        assertNotNull(savedTask.getUpdated_Time());
        verify(taskStore).writeMyTasks(any());
    }

    @Test
    public void  shouldGetAllTasks() {
        when(taskStore.readMyTasks()).thenReturn(tasks);

        List<MyTask> all = MyTaskService.getAll();

        assertEquals(tasks, all);
    }

    @Test
    public void shouldFindTask() {
        tasks.add(new MyTask(1L, "task"));
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.find(1L);

        MyTask task = optionalTask.get();
        assertEquals(1L, task.getId());
        assertEquals("task", task.getcontent());
    }

    @Test
    public void  shouldGetEmptyTask() {
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.find(1L);

        assertFalse(optionalTask.isPresent());
    }

    @Test
    public void shouldUpdateTask() {
        tasks.add(new MyTask(1L, "task"));
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.update(new MyTask(1L, "new task"));

        MyTask task = optionalTask.get();
        assertEquals(1L, task.getId());
        assertEquals("new task", task.getcontent());
        assertNotNull(task.getUpdated_Time());
        verify(taskStore).writeMyTasks(any());
    }

    @Test
    public void shouldNotUpdateTaskWhenNotExist() {
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.update(new MyTask(1L, "new task"));

        assertFalse(optionalTask.isPresent());
        verify(taskStore, new Times(0)).writeMyTasks(any());
    }

    @Test
    public void shouldDeleteTask() {
        tasks.add(new MyTask(1L, "task"));
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.delete(1L);

        MyTask task = optionalTask.get();
        assertEquals(1L, task.getId());
        verify(taskStore).writeMyTasks(any());
    }

    @Test
    public void shouldNotDeleteTaskWhenNotExist() {
        when(taskStore.readMyTasks()).thenReturn(tasks);

        Optional<MyTask> optionalTask = MyTaskService.delete(1L);

        assertFalse(optionalTask.isPresent());
        verify(taskStore, new Times(0)).writeMyTasks(any());
    }
}
