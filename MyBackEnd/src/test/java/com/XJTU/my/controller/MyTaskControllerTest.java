package com.XJTU.my.controller;

import com.google.gson.Gson;
import com.XJTU.my.model.MyTask;
import com.XJTU.my.service.MyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyTaskControllerTest.class)
public class MyTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyService service;

    private List<MyTask> tasks = new ArrayList<MyTask>();

    @BeforeEach
    void setUp() {
        tasks.add(new MyTask(1L, "a"));
    }

    @Test
    public void shouldGetAll() throws Exception {
        when(service.getAll()).thenReturn(tasks);
        this.mockMvc.perform(get("/myapi/mytasks")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("a"));
    }

    @Test
    public void shouldFindTaskByTask_idIfPresent() throws Exception {
        when(service.find(3L)).thenReturn(Optional.of(new MyTask(3L, "X")));
        this.mockMvc.perform(get("/myapi/mytasks/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("X"));
    }

    @Test
    public void shouldReturnNotFoundWhenFindByTask_idIfNotPresent() throws Exception {
        when(service.find(3L)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/myapi/mytasks/3")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteWhenExist() throws Exception {
        when(service.delete(2L)).thenReturn(Optional.of(new MyTask(2L, "B")));
        this.mockMvc.perform(delete("/myapi/mytasks/2")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeleteIfNotPresent() throws Exception {
        when(service.delete(2L)).thenReturn(Optional.empty());
        this.mockMvc.perform(delete("/myapi/mytasks/2")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        MyTask task = new MyTask(1L, "new");
        MyTask savedTask = new MyTask(1L, "new");
        when(service.saveMyTask(task)).thenReturn(savedTask);
        this.mockMvc.perform(post("/myapi/mytasks")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldChangeTaskByTask_id() throws Exception {
        MyTask task = new MyTask(2L, "Updated_Time");
        MyTask updated = new MyTask(1L, "Updated_Time");
        when(service.update(any())).thenReturn(Optional.of(updated));
        this.mockMvc.perform(put("/myapi/mytasks/1")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated_Time"));
    }

    @Test
    public void shouldReturnNotFoundWhenChangeTaskButDoesNotExit() throws Exception {
        MyTask task = new MyTask(2L, "Updated_Time");
        when(service.update(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(put("/myapi/mytasks/1")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(task)))
                .andDo(print()).andExpect(status().isNotFound());
    }
}