package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.Random;

public class DashboardController {

    @FXML
    private GridPane grid;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label taskLabel;

    private Label[][] cells = new Label[3][10];

    private String currentUser = "guest";
    private int day = 0; // 🔥 start at 0

    private final String[] tasks = {
            "Compliment someone sincerely",
            "Help someone carry something",
            "Thank a teacher",
            "Give someone encouragement",
            "Share food with a friend",
            "Hold the door for someone",
            "Send a kind message",
            "Help someone study"
    };

    @FXML
    public void initialize() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {

                Label cell = new Label("");
                cell.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-font-size: 16;");

                grid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }

        loadProgress();
    }

    @FXML
    public void generateTask() {
        Random random = new Random();
        int index = random.nextInt(tasks.length);
        taskLabel.setText(tasks[index]);
    }

    public void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            this.currentUser = username;
            welcomeLabel.setText("Welcome " + username);

            loadProgress();
        }
    }

    @FXML
    public void completeTask() {
        if (day >= 30) {
            taskLabel.setText("Grid is FULL! ⚠️");
            return;
        }

        int row = day / 10;
        int col = day % 10;

        cells[row][col].setText("C");
        cells[row][col].setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");

        saveProgress("C");

        day++;
        taskLabel.setText("Task Completed! ✅");
    }

    @FXML
    public void failTask() {
        if (day >= 30) {
            taskLabel.setText("Grid is FULL! ⚠️");
            return;
        }

        int row = day / 10;
        int col = day % 10;

        cells[row][col].setText("F");
        cells[row][col].setStyle("-fx-background-color: lightcoral; -fx-border-color: black;");

        saveProgress("F");

        day++;
        taskLabel.setText("Task Failed! ❌");
    }

    // 🔥 FIXED SAVE (NO "Day X")
    private void saveProgress(String status) {
        try {
            FileWriter writer = new FileWriter("progress_" + currentUser + ".txt", true);
            writer.write(status + "\n"); // 🔥 ONLY C OR F
            writer.close();
        } catch (IOException e) {
            taskLabel.setText("Error saving progress");
        }
    }

    // 🔥 FIXED LOAD
    private void loadProgress() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("progress_" + currentUser + ".txt")
            );

            String line;
            int index = 0;

            while ((line = reader.readLine()) != null && index < 30) {

                int row = index / 10;
                int col = index % 10;

                if (line.equals("C")) {
                    cells[row][col].setText("C");
                    cells[row][col].setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
                } else if (line.equals("F")) {
                    cells[row][col].setText("F");
                    cells[row][col].setStyle("-fx-background-color: lightcoral; -fx-border-color: black;");
                }

                index++;
            }

            day = index;

            reader.close();

        } catch (IOException e) {
            // first run
        }
    }

    @FXML
    public void resetProgress() {
        File file = new File("progress_" + currentUser + ".txt");
        if (file.exists()) {
            file.delete();
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col].setText("");
                cells[row][col].setStyle("-fx-border-color: black; -fx-padding: 10; -fx-font-size: 16;");
            }
        }

        day = 0;
        taskLabel.setText("Progress reset! 🔄");
    }
}