package com.gui.controllerFX;

import com.gui.config.Status;
import com.gui.domain.QuotationsMap;
import com.gui.domain.simple.Share;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.ResourceBundle;

public class QuotationsController implements Initializable {

    @FXML
    private TableView<Share> currentQuotations;

    @FXML
    private TableColumn<Share, String> name;

    @FXML
    private TableColumn<Share, String> currentPrice;

    @FXML
    private TableColumn<Share, String> actualizationDate;

    @FXML
    private Button closeButton;

    @FXML
    private TextField shareFilter;

    private Logger logger = Logger.getLogger(QuotationsController.class);
    private ObservableList<Share> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setSortType(TableColumn.SortType.ASCENDING);
        data = FXCollections.observableArrayList(QuotationsMap.getData().values());

        logger.info("Start initialize the quotations table, quantity: " + QuotationsMap.getData().size());

        name.setCellValueFactory(new PropertyValueFactory<>("index"));
        currentPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        actualizationDate.setCellValueFactory(new PropertyValueFactory<>("serverActualization"));

        name.setStyle( "-fx-alignment: CENTER-LEFT;");
        currentPrice.setStyle( "-fx-alignment: CENTER;");
        actualizationDate.setStyle( "-fx-alignment: CENTER;");

        logger.info("Setting the data, quantity: " + data.size());
        currentQuotations.setItems(data);
        currentQuotations.getSortOrder().add(name);
    }

    public void filterAction() {
        FilteredList<Share> filteredData = new FilteredList<>(data, p -> true);

        shareFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(share -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toUpperCase();
                return (share.getIndex().toUpperCase().contains(lowerCaseFilter));
            });
        });
        SortedList<Share> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(currentQuotations.comparatorProperty());
        currentQuotations.setItems(sortedData);
        currentQuotations.getSortOrder().add(name);
    }

    public void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Status.setQuotationsStageStatus(false);
        logger.info("Quotations stage closed");
    }

}
