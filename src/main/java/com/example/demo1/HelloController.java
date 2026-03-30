package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HelloController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    // Use a fixed file name that points to the project root
    private final String DATABASE_FILE = "users.txt";

    @FXML
    protected void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter username and password");
            return;
        }

        File file = new File(DATABASE_FILE);
        if (!file.exists()) {
            messageLabel.setText("No users registered yet");
            return;
        }

        // Try-with-resources ensures the scanner closes automatically
        try (Scanner scanner = new Scanner(file)) {
            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();

                    // Using equalsIgnoreCase for username makes login easier
                    if (storedUser.equalsIgnoreCase(username) && storedPass.equals(password)) {
                        found = true;
                        navigateToDashboard(username);
                        break;
                    }
                }
            }

            if (!found) {
                messageLabel.setText("Invalid username or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error loading dashboard");
        }
    }

    @FXML
    protected void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter username and password");
            return;
        }

        // 'true' means append to the file instead of overwriting it
        try (FileWriter writer = new FileWriter(DATABASE_FILE, true)) {
            writer.write(username + "," + password + System.lineSeparator());
            messageLabel.setText("User Registered! You can now login.");

            // Clear fields after success
            usernameField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error saving user");
        }
    }

    private void navigateToDashboard(String username) throws IOException {
        // Ensure this path matches your folder structure in src/main/resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/dashboard.fxml"));
        Parent root = loader.load();

        // Get the dashboard controller and pass the username
        DashboardController controller = loader.getController();
        controller.setUsername(username);

        // Switch the scene
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}