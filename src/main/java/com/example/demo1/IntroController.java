package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IntroController {

    @FXML
    private Button continueButton;

    @FXML
    private void handleContinue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credits");
        alert.setHeaderText("App Credits");
        alert.setContentText(
                "Verhel - Programmer/Designer\n" +
                        "Lu - Designer\n" +
                        "Angeles - Programmer"
        );
        alert.showAndWait();
    }

    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About Kindness App");
        alert.setContentText(
                "Welcome to Kindness App. In this app it generates different act of kindness " +
                        "that you can do and enjoy for a couple of days.\n\n" +
                        "You can use this app to track if you've completed or failed your task at hand.\n\n" +
                        "Have fun!"
        );
        alert.showAndWait();
    }
}
