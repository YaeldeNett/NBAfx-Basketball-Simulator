package controller;

import java.util.HashSet;
import java.util.Set;

import utils.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Game;
import model.Season;
import model.Team;
import javafx.scene.control.*;
import javafx.collections.ListChangeListener;

public class TeamsRoundController extends Controller<Season> {

    @FXML
    private ListView<Team> teamListView;

    @FXML
    private Button addButton, arrangeSeasonButton;

    @FXML
    private TableView<Game> gameTableView;

    @FXML
    private TableColumn<Game, Integer> gameCol;

    @FXML
    private TableColumn<Game, String> team1Col, team2Col;

    @FXML
    private Label roundLabel;

    private final Label gameTablePlaceHolder = new Label("No teams added to round");
    private final Label teamListPlaceHolder = new Label("All teams added to round.");

    private final ObservableList<Team> availableTeams = FXCollections.observableArrayList();
    private final ObservableList<Game> selectedGames = FXCollections.observableArrayList();

    public Season getSeason() {
        return this.model;
    }

    // Initializes the round view and listeners
    @FXML
    public void initialize() {
        teamListView.setItems(availableTeams);
        teamListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gameCol.setCellValueFactory(new PropertyValueFactory<>("term"));

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

        gameTableView.setItems(selectedGames);
        selectedGames.setAll(getSeason().getCurrentSchedule());
        updateRoundLabel();
        refreshAvailableTeams();

        gameTableView.setPlaceholder(gameTablePlaceHolder);
        teamListView.setPlaceholder(teamListPlaceHolder);

        teamListView.getSelectionModel().getSelectedItems()
                .addListener((ListChangeListener.Change<? extends Team> c) -> {
                    addButton.setDisable(teamListView.getSelectionModel().getSelectedItems().size() != 2);
                });

        addButton.setDisable(true);
    }

    // Adds selected teams to the game
    @FXML
    private void handleAddTeamToGame() {
        ObservableList<Team> selectedTeams = teamListView.getSelectionModel().getSelectedItems();

        if (selectedTeams.size() == 2) {
            getSeason().addTeams(selectedTeams);
            selectedGames.setAll(getSeason().getCurrentSchedule());
            refreshAvailableTeams();
            teamListView.getSelectionModel().clearSelection();
        }
    }

    // Closes the round arrangement window
    @FXML
    private void arrangeRounds() {
        Stage stage = (Stage) arrangeSeasonButton.getScene().getWindow();
        stage.close();
    }

    private void updateRoundLabel() {
        roundLabel.setText("Round: " + (getSeason().round() + 1));
    }

    // Updates the list of available teams
    private void refreshAvailableTeams() {
        Set<Team> scheduledTeams = new HashSet<>();
        for (Game game : getSeason().getCurrentSchedule()) {
            scheduledTeams.addAll(game.getCurrentTeams());
        }

        ObservableList<Team> allTeams = getSeason().getCurrentTeams();
        ObservableList<Team> eligibleTeams = FXCollections.observableArrayList();
        for (Team team : allTeams) {
            if (!scheduledTeams.contains(team)) {
                eligibleTeams.add(team);
            }
        }

        availableTeams.setAll(eligibleTeams);
    }
}