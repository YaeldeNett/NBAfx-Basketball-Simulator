package controller;

import java.io.IOException;

import utils.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;
import model.Team;

public class ManageTeamController extends Controller<Team> {

    @FXML
    private TextField teamNameField;

    @FXML
    private TableView<Player> teamTableView;

    @FXML
    private Button addButton, updateButton, deleteButton, saveAndCloseButton;

    private final Validator validator = new Validator();

    // Initializes the controller and listeners
    public void initialize() {
        if (model != null) {
            teamNameField.setText(model.getName());
            teamTableView.setItems(model.getCurrentPlayers());

            updateButton.setDisable(true);
            deleteButton.setDisable(true);

            teamTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                boolean playerSelected = (newValue != null);
                updateButton.setDisable(!playerSelected);
                deleteButton.setDisable(!playerSelected);
                addButton.setDisable(playerSelected);
            });
        }
    }

    // Opens the window to add a new player
    @FXML
    private void addPlayer() {
        try {
            Player newPlayer = new Player("", 0.0, 0, 0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayerUpdateView.fxml"));
            Parent root = loader.load();

            PlayerUpdateController controller = loader.getController();
            controller.setModel(newPlayer);
            controller.setParentTeam(model);
            controller.setEditMode(false);

            controller.initialize();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/view/edit.png"));
            stage.setTitle("Adding New Player");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Opens the window to update an existing player
    @FXML
    private void updatePlayer() {
        Player selectedPlayer = teamTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer != null) {
            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/view/edit.png"));
                String title = "Updating Player: " + selectedPlayer.getName();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayerUpdateView.fxml"));
                Parent root = loader.load();
                PlayerUpdateController controller = loader.getController();
                controller.setModel(selectedPlayer);
                controller.setParentTeam(model);
                controller.setEditMode(true);

                controller.initialize();

                stage.setScene(new Scene(root));
                stage.setTitle(title);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Deletes the selected player
    @FXML
    private void deletePlayer() {
        Player selectedPlayer = teamTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer != null) {
            model.getCurrentPlayers().remove(selectedPlayer);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateTeamName(String name) {
        validator.clear();
        validator.generateErrors(name);

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

    // Validates and saves changes, then closes the window
    @FXML
    private void saveAndClose() {
        String newTeamName = teamNameField.getText();

        if (validateTeamName(newTeamName)) {
            model.setName(newTeamName.trim());
            Stage stage = (Stage) saveAndCloseButton.getScene().getWindow();
            stage.close();
        }
    }
}
