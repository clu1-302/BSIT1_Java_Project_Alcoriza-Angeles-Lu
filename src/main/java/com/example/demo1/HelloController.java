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


        try (Scanner scanner = new Scanner(file)) {
            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();


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


        try (FileWriter writer = new FileWriter(DATABASE_FILE, true)) {
            writer.write(username + "," + password + System.lineSeparator());
            messageLabel.setText("User Registered! You can now login.");


            usernameField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error saving user");
        }
    }

    private void navigateToDashboard(String username) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/dashboard.fxml"));
        Parent root = loader.load();


        DashboardController controller = loader.getController();
        controller.setUsername(username);


        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}