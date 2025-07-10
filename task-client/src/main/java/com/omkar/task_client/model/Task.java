package com.omkar.task_client.model;

import jdk.jfr.DataAmount;
import lombok.Data;

@Data

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long userId;

}
