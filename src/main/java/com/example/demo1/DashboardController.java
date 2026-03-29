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
    private int day = 0; // ✅ start at 0 (IMPORTANT)

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

                Label cell = new Label(""); // cleaner UI
                applyBaseCellStyle(cell);

                grid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }

        loadProgress(); // load saved data
    }

    private void applyBaseCellStyle(Label cell) {
        cell.setStyle(
                "-fx-border-color: black;" +
                        "-fx-padding: 20;" +
                        "-fx-font-size: 20;" +
                        "-fx-min-width: 50;" +
                        "-fx-min-height: 50;" +
                        "-fx-alignment: center;"
        );
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

        updateGridCell("C", "lightgreen");
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

        updateGridCell("F", "lightcoral");
        saveProgress("F");

        day++;
        taskLabel.setText("Task Failed! ❌");
    }

    private void updateGridCell(String text, String color) {
        int row = day / 10;
        int col = day % 10;

        cells[row][col].setText(text);
        cells[row][col].setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-border-color: black;" +
                        "-fx-padding: 20;" +
                        "-fx-font-size: 20;" +
                        "-fx-min-width: 50;" +
                        "-fx-min-height: 50;" +
                        "-fx-alignment: center;"
        );
    }

    private void saveProgress(String status) {
        try (FileWriter writer = new FileWriter("progress_" + currentUser + ".txt", true)) {
            writer.write(status + "\n"); // ✅ FIXED (no "Day X")
        } catch (IOException e) {
            taskLabel.setText("Error saving progress");
        }
    }

    private void loadProgress() {
        File file = new File("progress_" + currentUser + ".txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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

            day = index; // ✅ FIXED

        } catch (IOException e) {
            e.printStackTrace();
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
                applyBaseCellStyle(cells[row][col]);
            }
        }

        day = 0;
        taskLabel.setText("Progress reset! 🔄");
    }
}