package com.XJTU.my.store;

import com.google.gson.*;
import com.XJTU.my.model.MyTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MyStore {
    @Value("${todo.store.file_name}")
    private String file_name;
    @Value("${env}")
    private String env;

    public List<MyTask> readMyTasks() {
        try {
            String contents = new String(Files.readAllBytes(getFile().toPath()));
            MyTask[] MyTasks = getGson().fromJson(contents, MyTask[].class);
            return Arrays.asList(MyTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void writeMyTasks(List<MyTask> MyTasks) {
        try {
            FileWriter fileWriter = new FileWriter(getFile().getAbsolutePath());
            fileWriter.write(getGson().toJson(MyTasks));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        if (env.equals("test")) {
            return new File(getClass().getClassLoader().getResource(file_name).getFile());
        }
        return new File(file_name);
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, typeOfT, context) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .create();
    }
}
