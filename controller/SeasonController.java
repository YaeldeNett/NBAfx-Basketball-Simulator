package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Controller;
import utils.ViewLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Association;
import model.Record;
import model.Season;

public class SeasonController extends Controller<Association> {
    private static final Logger logger = Logger.getLogger(SeasonController.class.getName());

    @FXML
    private Button closeButton, roundButton, currentRoundButton, resultButton;

    private Season cachedSeason = null;

    private Season getManagedSeason() {
        if (cachedSeason == null) {
            cachedSeason = this.model.getSeason();
        }
        return cachedSeason;
    }

    // Opens the view to arrange season rounds
    @FXML
    public void arrangeRounds() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            Season season = getManagedSeason();

            ViewLoader.showStage(season, "/view/SeasonRoundView.fxml", "Season Rounds", stage);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error showing the TeamsRoundView stage.", ex);
        }
    }

    // Shows the current round of games
    @FXML
    public void viewCurrentRounds() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            Season season = getManagedSeason();

            ViewLoader.showStage(season, "/view/CurrentRoundTeams.fxml", "Tournament", stage);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error showing the TeamsRoundView stage.", ex);
        }
    }

    // Simulates the games for the current round
    @FXML
    public void playGame() {
        Season season = getManagedSeason();

        String message = season.playGame();

        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.setTitle("Game Results");
            stage.getIcons().add(new Image("/view/error.png"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Parent root = loader.load();
            ErrorController controller = loader.getController();
            controller.setMessage(message);

            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error displaying the results window.", ex);
        }
    }

    // Displays the season records
    @FXML
    public void viewResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordView.fxml"));
            Parent root = loader.load();

            RecordController controller = loader.getController();
            ObservableList<Record> records = getManagedSeason().record();
            controller.initData(records);

            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            stage.setTitle("Season Record");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error showing the record view stage.", ex);
        }
    }

    // Closes the window
    @FXML
    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}