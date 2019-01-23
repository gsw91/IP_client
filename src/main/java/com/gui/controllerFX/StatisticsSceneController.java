package com.gui.controllerFX;

import com.gui.config.Status;
import com.gui.domain.StatisticsRecord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

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

    @FXML
    private Button closeButton;

    private Logger logger = Logger.getLogger(StatisticsSceneController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Status.setStatisticsStageStatus(false);
        logger.info("Statistics stage closed");
    }

}
