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

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // LOGIN
    @FXML
    protected void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            messageLabel.setText("Enter username and password");
            return;
        }

        try {

            File file = new File("users.txt");

            if(!file.exists()){
                messageLabel.setText("No users registered yet");
                return;
            }

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){

                String line = scanner.nextLine().trim();
                String[] parts = line.split(",");

                if(parts.length == 2){

                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();

                    if(storedUser.equals(username) && storedPass.equals(password)){

                        // 🔥 LOAD DASHBOARD
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/demo1/dashboard.fxml")
                        );

                        Parent root = loader.load();

                        // 🔥 PASS USERNAME
                        DashboardController controller = loader.getController();
                        controller.setUsername(username);

                        // 🔥 SWITCH SCENE
                        Stage stage = (Stage) usernameField.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();

                        scanner.close();
                        return;
                    }
                }
            }

            scanner.close();
            messageLabel.setText("Invalid username or password");

        }catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error loading dashboard");
        }
    }

    // REGISTER
    @FXML
    protected void handleRegister() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            messageLabel.setText("Enter username and password");
            return;
        }

        try {

            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + "," + password + "\n");
            writer.close();

            messageLabel.setText("User Registered!");

        } catch (IOException e) {
            e.printStackTrace(); // 👈 DEBUG
            messageLabel.setText("Error saving user");
        }
    }
}