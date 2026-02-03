package controller;

import utils.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Team;
import model.Teams;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTeamController extends Controller<Teams> {

    @FXML
    private TextField teamNameField;

    @FXML
    private Button addButton;

    private final Validator validator = new Validator();

    // Handles the logic for adding a new team
    @FXML
    private void addTeam() {
        String teamName = teamNameField.getText().trim();

        validator.clear();
        validator.generateErrors(teamName);

        if (model.hasTeam(teamName)) {
            validator.addError(teamName + " Team already exists.");
        }

        if (!validator.errors().isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (String error : validator.errors()) {
                errorMessages.append(error).append("\n");
            }
            showErrorWindow(errorMessages.toString());
            return;
        }

        Team newTeam = new Team(teamName);
        model.addTeam(newTeam);

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    // Displays an error window with the given message
    private void showErrorWindow(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Parent root = loader.load();

            ErrorController errorController = loader.getController();
            errorController.setMessage(message);

            Stage stage = new Stage();
            stage.setTitle("Input Errors");
            stage.getIcons().add(new Image("/view/error.png"));
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}