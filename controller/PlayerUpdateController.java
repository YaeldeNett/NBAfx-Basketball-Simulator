package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;
import model.Team;

public class PlayerUpdateController extends Controller<Player> {

    @FXML
    private TextField playerNameField, playerCreditField, playerAgeField, playerNoField;

    @FXML
    private Button updateButton, addButton, closeButton;

    private Team parentTeam;

    private boolean isEditMode;

    private final Validator validator = new Validator();

    public void setModel(Player player) {
        this.model = player;
    }

    public void setParentTeam(Team team) {
        this.parentTeam = team;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    // Sets up the view based on add/edit mode
    public void initialize() {
        if (model != null) {
            if (isEditMode) {
                playerNameField.setText(model.getName());
                playerCreditField.setText(String.valueOf(model.getCredit()));
                playerAgeField.setText(String.valueOf(model.getAge()));
                playerNoField.setText(String.valueOf(model.getNo()));

                addButton.setDisable(true);
                updateButton.setDisable(false);
            } else {
                playerNameField.clear();
                playerCreditField.clear();
                playerAgeField.clear();
                playerNoField.clear();

                addButton.setDisable(false);
                updateButton.setDisable(true);
            }
        }
    }

    // Updates the player details
    @FXML
    private void updatePlayer() {
        String name = playerNameField.getText();
        String credit = playerCreditField.getText();
        String age = playerAgeField.getText();
        String no = playerNoField.getText();

        if (validateInput(name, credit, age, no)) {
            model.update(name, Double.parseDouble(credit), Integer.parseInt(age), Integer.parseInt(no));
            addButton.setDisable(true);
            updateButton.setDisable(false);
        }
    }

    // Adds a new player to the team
    @FXML
    private void addPlayer() {
        String name = playerNameField.getText();
        String credit = playerCreditField.getText();
        String age = playerAgeField.getText();
        String no = playerNoField.getText();

        if (validateInput(name, credit, age, no)) {
            model.update(name, Double.parseDouble(credit), Integer.parseInt(age), Integer.parseInt(no));
            model.setTeam(parentTeam);

            parentTeam.getCurrentPlayers().add(model);

            addButton.setDisable(false);
            updateButton.setDisable(true);
            closeWindow();
        }
    }

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
            Logger.getLogger(PlayerUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Validates the user input
    private boolean validateInput(String name, String credit, String age, String no) {
        validator.clear();
        validator.generateErrors(name, credit, age, no);

        if (!validator.errors().isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (String error : validator.errors()) {
                errors.append(error);
            }
            showErrorWindow(errors.toString());
            return false;
        }
        return true;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}