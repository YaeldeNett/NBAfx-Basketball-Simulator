package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private Text messageText;

    @FXML
    private Button okayButton;

    // Closes the error window
    @FXML
    private void close() {
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }

    // Sets the error message to display
    public void setMessage(String message) {
        messageText.setText(message);
    }
}