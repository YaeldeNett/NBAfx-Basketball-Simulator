package controller;

import utils.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Game;
import model.Season;
import model.Team;
import javafx.scene.control.TableCell;

public class CurrentRoundTeamsController extends Controller<Season> {

    @FXML
    private TableView<Game> currentTeamsTable;

    @FXML
    private TableColumn<Game, String> vsCol, team2Col, team1Col;

    @FXML
    private Label roundLabel;

    private final Label placeholderLabel = new Label("No teams to show");

    public Season getSeason() {
        return this.model;
    }

    // Initializes the table columns and data
    @FXML
    public void initialize() {
        team1Col.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    Game currentGame = getTableView().getItems().get(getIndex());
                    ObservableList<Team> teams = currentGame.getCurrentTeams();
                    setText(teams.size() >= 1 ? teams.get(0).getName() : "");
                }
            }
        });

        vsCol.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    setText("VS");
                }
            }
        });

        team2Col.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    Game currentGame = getTableView().getItems().get(getIndex());
                    ObservableList<Team> teams = currentGame.getCurrentTeams();
                    setText(teams.size() >= 2 ? teams.get(1).getName() : "");
                }
            }
        });

        Season currentSeason = getSeason();

        roundLabel.setText("Round: " + (currentSeason.round() + 1));

        ObservableList<Game> currentSchedule = currentSeason.getCurrentSchedule();
        currentTeamsTable.setItems(currentSchedule);

        currentTeamsTable.setPlaceholder(placeholderLabel);
    }

    // Closes the window
    @FXML
    private void close() {
        Stage stage = (Stage) currentTeamsTable.getScene().getWindow();
        stage.close();
    }
}