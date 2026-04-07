package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DashboardController {

    @FXML private GridPane grid;
    @FXML private Label welcomeLabel;
    @FXML private Label taskLabel;

    private Label[][] cells = new Label[3][10];
    private String currentUser = "guest";
    private int day = 0;

    private final String[] tasks = {
            "Compliment someone sincerely", "Help someone carry something",
            "Thank a teacher", "Give someone encouragement",
            "Share food with a friend", "Hold the door for someone",
            "Send a kind message", "Help someone study",
            "Smile at a stranger", "Listen to someone without interrupting",
            "Offer help to a classmate", "Pick up litter around you",
            "Say sorry if you made a mistake", "Cheer someone up",
            "Give up your seat for someone", "Write a thank you note",
            "Help clean a shared space", "Invite someone to join you",
            "Be patient in a long line", "Say something positive to yourself",
            "Help someone with directions", "Encourage someone who is struggling",
            "Donate something you no longer use", "Check in on a friend",
            "Respect someone’s opinion", "Give genuine praise",
            "Offer to help at home", "Let someone go first",
            "Be kind to someone having a bad day", "Say good morning to people"

    };

    @FXML
    public void initialize() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {
                Label cell = new Label("");
                applyBaseCellStyle(cell);
                grid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
        loadProgress();
    }

    private void applyBaseCellStyle(Label cell) {
        cell.setText(""); // Clear text
        cell.setStyle(
                "-fx-border-color: black;" +
                        "-fx-padding: 20;" +
                        "-fx-font-size: 20;" +
                        "-fx-min-width: 50;" +
                        "-fx-min-height: 50;" +
                        "-fx-alignment: center;" +
                        "-fx-background-color: transparent;"
        );
    }

    @FXML
    public void generateTask() {
        Random random = new Random();
        taskLabel.setText(tasks[random.nextInt(tasks.length)]);
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

    @FXML
    public void undoTask() {
        if (day <= 0) {
            taskLabel.setText("Nothing to undo! ↩️");
            return;
        }

        // 1. Move back one day
        day--;

        // 2. Clear the cell in UI
        int row = day / 10;
        int col = day % 10;
        applyBaseCellStyle(cells[row][col]);

        // 3. Remove last line from file
        removeLastLineFromFile();
        taskLabel.setText("Last action undone! ↩️");
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
            writer.write(status + "\n");
        } catch (IOException e) {
            taskLabel.setText("Error saving progress");
        }
    }

    private void removeLastLineFromFile() {
        File file = new File("progress_" + currentUser + ".txt");
        if (!file.exists()) return;

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) { e.printStackTrace(); }

        // Remove the last item if list isn't empty
        if (!lines.isEmpty()) {
            lines.remove(lines.size() - 1);
        }

        // Rewrite the file with the remaining lines
        try (FileWriter writer = new FileWriter(file, false)) { // 'false' overwrites
            for (String l : lines) {
                writer.write(l + "\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
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
                String color = line.equals("C") ? "lightgreen" : "lightcoral";
                cells[row][col].setText(line);
                cells[row][col].setStyle(
                        "-fx-background-color: " + color + ";" +
                                "-fx-border-color: black;" +
                                "-fx-padding: 20;" +
                                "-fx-font-size: 20;" +
                                "-fx-min-width: 50;" +
                                "-fx-min-height: 50;" +
                                "-fx-alignment: center;");
                index++;
            }
            day = index;
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    public void resetProgress() {
        File file = new File("progress_" + currentUser + ".txt");
        if (file.exists()) file.delete();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {
                applyBaseCellStyle(cells[row][col]);
            }
        }
        day = 0;
        taskLabel.setText("Progress reset! 🔄");
    }
}