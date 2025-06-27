package com.omkar.task_client.ui;

import com.omkar.task_client.model.Task;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
//import lombok.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
@Data
@Component
public class TaskGui extends JFrame{

    @Value("${task-service.url}")
    private String taskServiceUrl;

    @Value("${user-service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;
    private JTextField usernameField, passwordField, taskTitleField, taskDescField, userIdField;
    private JTextArea resultArea;
    private String jwtToken;

    public TaskGui(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        initComponents();
    }

    private void initComponents() {
        setTitle("Task Management Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        loginPanel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        JPanel taskPanel = new JPanel(new GridLayout(4, 2));
        taskPanel.add(new JLabel("Task Title:"));
        taskTitleField = new JTextField(10);
        taskPanel.add(taskTitleField);
        taskPanel.add(new JLabel("Task Description:"));
        taskDescField = new JTextField(10);
        taskPanel.add(taskDescField);
        taskPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField(10);
        taskPanel.add(userIdField);
        JButton createTaskButton = new JButton("Create Task");
        taskPanel.add(createTaskButton);

        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(loginPanel, BorderLayout.NORTH);
        add(taskPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        loginButton.addActionListener(this::login);
        createTaskButton.addActionListener(this::createTask);
    }

    private void login(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        //String loginUrl = userServiceUrl + "/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            //jwtToken = restTemplate.postForObject(loginUrl, request, String.class);
            resultArea.append("Login successful! Token: " + jwtToken + "\n");
        } catch (Exception ex) {
            resultArea.append("Login failed: " + ex.getMessage() + "\n");
        }
    }

    private void createTask(ActionEvent e) {
        Task task = new Task();
        task.setTitle(taskTitleField.getText());
        task.setDescription(taskDescField.getText());
        try {
            task.setUserId(Long.parseLong(userIdField.getText()));
        } catch (NumberFormatException ex) {
            resultArea.append("Invalid User ID\n");
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> request = new HttpEntity<>(task, headers);

        try {
            Task response = restTemplate.postForObject(taskServiceUrl, request, Task.class);
            resultArea.append("Task created: " + response.getTitle() + "\n");
        } catch (Exception ex) {
            resultArea.append("Task creation failed: " + ex.getMessage() + "\n");
        }
    }
}
