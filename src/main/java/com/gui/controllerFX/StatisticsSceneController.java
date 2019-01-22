package com.gui.controllerFX;

import com.gui.domain.StatisticsRecord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsSceneController implements Initializable {

    @FXML
    private TableView<StatisticsRecord> userStatistis;

    @FXML
    private TableColumn<StatisticsRecord, String> name;

    @FXML
    private TableColumn<StatisticsRecord, String> buyingPrice;

    @FXML
    private TableColumn<StatisticsRecord, String> sellingPrice;

    @FXML
    private TableColumn<StatisticsRecord, String> quantity;

    @FXML
    private TableColumn<StatisticsRecord, String> result;

    @FXML
    private TableColumn<StatisticsRecord, String> returnRate;

    @FXML
    private TableColumn<StatisticsRecord, String> buyingDate;

    @FXML
    private TableColumn<StatisticsRecord, String> sellingDate;

    @FXML
    private TableColumn<StatisticsRecord, String> duration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeButtonAction() {

    }

}
