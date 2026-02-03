package controller;

import utils.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;
import model.Teams;

public class ViewPlayersController extends Controller<Player> {

    @FXML
    private TableView<Player> tableView;
    @FXML
    private TableColumn<Player, String> teamCol, nameCol, levelCol;
    @FXML
    private TableColumn<Player, Double> creditCol;
    @FXML
    private TableColumn<Player, Integer> ageCol, noCol;

    @FXML
    private TextField levelFilterField, nameFilterField, ageFromField, ageToField;
    @FXML
    private Button closeButton;

    private final ObservableList<Player> players = FXCollections.observableArrayList();
    private Teams teams;

    private final Label placeholderLabel = new Label("Players list is not loaded.");

    // Sets the teams and refreshes the player list
    public void setTeams(Teams teams) {
        this.teams = teams;
        refreshPlayers();
    }

    public void refreshPlayers() {
        if (teams != null) {
            players.setAll(teams.allPlayersList());
        }
    }

    // Initializes the player table and filters
    public void initialize() {
        teamCol.setCellValueFactory(cellData -> {
            String teamName = cellData.getValue().getTeamName() != null ? cellData.getValue().getTeamName() : "Unknown";
            return new javafx.beans.property.SimpleStringProperty(teamName);
        });
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        levelCol.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        creditCol.setCellValueFactory(cellData -> cellData.getValue().getPlayerCreditProperty().asObject());
        ageCol.setCellValueFactory(cellData -> cellData.getValue().getPlayerAgeProperty().asObject());
        noCol.setCellValueFactory(cellData -> cellData.getValue().getPlayerNoProperty().asObject());

        ageFromField.setText("0");
        ageToField.setText("0");

        FilteredList<Player> filteredPlayers = new FilteredList<>(players, p -> true);
        updateFilters(filteredPlayers);

        SortedList<Player> sortedPlayers = new SortedList<>(filteredPlayers);
        sortedPlayers.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedPlayers);
        tableView.setPlaceholder(placeholderLabel);
    }

    private void updateFilters(FilteredList<Player> filteredPlayers) {
        levelFilterField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters(filteredPlayers));
        nameFilterField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters(filteredPlayers));
        ageFromField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters(filteredPlayers));
        ageToField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters(filteredPlayers));
    }

    // Applies the search filters to the player list
    private void applyFilters(FilteredList<Player> filteredPlayers) {
        filteredPlayers.setPredicate(player -> {
            boolean levelMatches = levelFilterField.getText().isEmpty()
                    || player.getLevel().toLowerCase().contains(levelFilterField.getText().toLowerCase());
            boolean nameMatches = nameFilterField.getText().isEmpty()
                    || player.getName().toLowerCase().contains(nameFilterField.getText().toLowerCase());
            boolean ageMatches = isWithinAgeRange(player);

            return levelMatches && nameMatches && ageMatches;
        });
    }

    private boolean isWithinAgeRange(Player player) {
        String fromText = ageFromField.getText().trim();
        String toText = ageToField.getText().trim();
        int ageFrom, ageTo;

        try {
            ageFrom = fromText.isEmpty() ? 0 : Integer.parseInt(fromText);
            ageTo = toText.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(toText);
        } catch (NumberFormatException e) {
            return false;
        }

        if (ageFrom == 0 && ageTo == 0) {
            return true;
        }

        return player.getAge() >= ageFrom && player.getAge() <= ageTo;
    }

    // Closes the window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}