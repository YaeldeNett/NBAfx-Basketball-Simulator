package controller;

import utils.ViewLoader;
import utils.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Teams;

public class ExploreTeamsController extends Controller<Teams> {

    @FXML
    private Button viewPlayersButton;

    @FXML
    private Button teamsMenuButton;

    @FXML
    private Button closeButton;

    public Teams getTeams() {
        return this.model;
    }

    // Opens the Teams Menu
    @FXML
    private void teamsMenu() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            ViewLoader.showStage(getTeams(), "/view/TeamsTable.fxml", "Teams Menu", stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Opens the View Players window
    @FXML
    private void viewPlayers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersView.fxml"));
            Parent root = loader.load();

            ViewPlayersController controller = loader.getController();
            controller.setTeams(getTeams());

            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            stage.setScene(new Scene(root));
            stage.setTitle("Players");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ExploreTeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Closes the window
    @FXML
    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}