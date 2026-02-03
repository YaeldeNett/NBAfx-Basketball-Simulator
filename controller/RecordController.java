package controller;

import utils.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Record;

public class RecordController extends Controller<ObservableList<Record>> {

    @FXML
    private TableView<Record> recordsTable;

    @FXML
    private Button closeButton;

    // Initializes the table with records
    public void initData(ObservableList<Record> records) {
        recordsTable.setItems(records);
        recordsTable.refresh();
    }

    // Closes the window
    @FXML
    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}