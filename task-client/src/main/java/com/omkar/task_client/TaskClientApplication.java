package com.omkar.task_client;

import com.omkar.task_client.ui.TaskGui;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TaskClientApplication {

	public static void main(String[] args) {

		System.setProperty("java.awt.headless", "false");

		ApplicationContext context =SpringApplication.run(TaskClientApplication.class, args);

		TaskGui taskGui = context.getBean(TaskGui.class);

		SwingUtilities.invokeLater(() -> {
			if (!GraphicsEnvironment.isHeadless()) {
				taskGui.setVisible(true);
			} else {
				System.err.println("Headless environment detected. Cannot initialize GUI.");
			}
		});

	}

}
