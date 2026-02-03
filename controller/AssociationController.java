package controller;

import utils.ViewLoader;
import utils.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import model.Association;

public class AssociationController extends Controller<Association> {

    @FXML
    private Button exploreTeamsButton, newSeasonButton, exitButton;

    public Association getAssociation() {
        return this.model;
    }

    // Opens the Explore Teams view
    @FXML
    public void exploreTeams() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            ViewLoader.showStage(getAssociation().getTeams(), "/view/ExploreTeamsView.fxml", "Explore Teams", stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Opens the Season View
    @FXML
    public void newSeason() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            ViewLoader.showStage(getAssociation(), "/view/SeasonView.fxml", "Arrange a new season", stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Exits the application
    @FXML
    public void exit() {
        Platform.exit();
    }
}