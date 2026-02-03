package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Controller;
import utils.ViewLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Team;
import model.Teams;

public class TeamsController extends Controller<Teams> {

    @FXML
    private Button addButton, manageButton, deleteButton, closeButton;

    @FXML
    private TableView<Team> teamsTV;

    public final Teams getTeams() {
        return model;
    }

    private Team getSelectedTeam() {
        return teamsTV.getSelectionModel().getSelectedItem();
    }

    // Initializes the teams table and listeners
    public void initialize() {
        ObservableList<Team> teamsList = getTeams().currentTeams();
        teamsTV.itemsProperty().unbind();
        teamsTV.setItems(teamsList);

        teamsTV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            addButton.setDisable(newValue != null);

            boolean noTeamSelected = (newValue == null);
            manageButton.setDisable(noTeamSelected);
            deleteButton.setDisable(noTeamSelected);
        });
    }

    // Opens the window to add a new team
    @FXML
    private void addTeam() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/edit.png"));

            ViewLoader.showStage(getTeams(), "/view/AddTeam.fxml", "Adding New Team", stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Opens the window to manage the selected team
    @FXML
    private void manageTeam() {
        Team selectedTeam = getSelectedTeam();
        String teamName = selectedTeam.getName();
        String title = "Managing Team: " + teamName;

        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            ViewLoader.showStage(selectedTeam, "/view/ManageTeamView.fxml", title, stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Deletes the selected team
    @FXML
    private void deleteTeam() {
        Team selectedTeam = getSelectedTeam();
        if (selectedTeam != null) {
            model.remove(selectedTeam);
        }
    }

    // Closes the window
    @FXML
    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
