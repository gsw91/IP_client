package com.gui.controllerFX;

import com.gui.component.Confirmation;
import com.gui.component.Information;
import com.gui.config.GuiStage;
import com.gui.config.ServiceConfig;
import com.gui.config.Status;
import com.gui.data.CalculationMap;
import com.gui.data.QuotationsMap;
import com.gui.data.RecordList;
import com.gui.domain.InstrumentCalculation;
import com.gui.domain.InstrumentRecord;
import com.gui.domain.User;
import com.gui.editor.Editor;
import com.gui.editor.TableCellColor;
import com.gui.editor.TableComparator;
import com.gui.scene.FirstScene;
import com.gui.scene.QuotationsScene;
import com.gui.service.InstrumentService;
import com.gui.service.UserOperation;
import com.gui.update.ThreadConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSceneController implements Initializable {

    @FXML
    private TableView<InstrumentRecord> userInstruments;

    @FXML
    private TableColumn<InstrumentRecord, String> name;

    @FXML
    private TableColumn<InstrumentRecord, String> quantity;

    @FXML
    private TableColumn<InstrumentRecord, String> buyingPrice;

    @FXML
    private TableColumn<InstrumentRecord, String> investedCapital;

    @FXML
    private TableColumn<InstrumentRecord, String> currentPrice;

    @FXML
    private TableColumn<InstrumentRecord, String> currentValuation;

    @FXML
    private TableColumn<InstrumentRecord, String> shareRatio;

    @FXML
    private TableColumn<InstrumentRecord, String> returnRate;

    @FXML
    private TableColumn<InstrumentRecord, String> result;

    @FXML
    private Label invested_capital;

    @FXML
    private Label portfolio_valuation;

    @FXML
    private Label return_rate;

    @FXML
    private Label portfolio_result;

    @FXML
    private Label userLabel;

    @FXML
    private Label labelAction;

    @FXML
    private Button confirmAction;

    @FXML
    private Button cancelAction;

    @FXML
    private TextField instrumentAction;

    @FXML
    private TextField quantityAction;

    @FXML
    private TextField priceAction;

    private ObservableList<InstrumentRecord> data;
    private Logger logger = Logger.getLogger(UserSceneController.class);
    private Editor editor = new Editor();
    private ThreadConfig threadConfig = new ThreadConfig(this);
    private AutoCompletionBinding<String> sharesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QuotationsMap.setCurrentQuotations();
        getUserInstruments();
        RecordList.reloadList(CalculationMap.getData());
        name.setSortType(TableColumn.SortType.ASCENDING);
        data = FXCollections.observableArrayList(RecordList.getRecordList());
        logger.info("Start initialize the user table, quantity: " + RecordList.getRecordList().size());

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        buyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        investedCapital.setCellValueFactory(new PropertyValueFactory<>("investedCapital"));
        currentPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        currentValuation.setCellValueFactory(new PropertyValueFactory<>("currentValuation"));
        shareRatio.setCellValueFactory(new PropertyValueFactory<>("shareRatio"));
        returnRate.setCellValueFactory(new PropertyValueFactory<>("returnRate"));
        result.setCellValueFactory(new PropertyValueFactory<>("result"));

        setFontColor();

        quantity.setSortable(false);
        shareRatio.setComparator(new TableComparator(TableComparator.PERCENTAGES));
        returnRate.setComparator(new TableComparator(TableComparator.PERCENTAGES));
        buyingPrice.setComparator(new TableComparator(TableComparator.PRICES));
        currentPrice.setComparator(new TableComparator(TableComparator.PRICES));
        investedCapital.setComparator(new TableComparator(TableComparator.AMOUNTS));
        currentValuation.setComparator(new TableComparator(TableComparator.AMOUNTS));
        result.setComparator(new TableComparator(TableComparator.AMOUNTS));

        name.setStyle( "-fx-alignment: CENTER-LEFT;");
        quantity.setStyle( "-fx-alignment: CENTER-RIGHT;");
        buyingPrice.setStyle( "-fx-alignment: CENTER-RIGHT;");
        investedCapital.setStyle( "-fx-alignment: CENTER-RIGHT;");
        currentPrice.setStyle( "-fx-alignment: CENTER-RIGHT;");
        currentValuation.setStyle( "-fx-alignment: CENTER-RIGHT;");
        returnRate.setStyle( "-fx-alignment: CENTER-RIGHT;");
        shareRatio.setStyle( "-fx-alignment: CENTER-RIGHT;");
        result.setStyle( "-fx-alignment: CENTER-RIGHT;");

        logger.info("Setting the data, quantity: " + data.size());
        userInstruments.setItems(data);
        userInstruments.getSortOrder().add(name);

        setValueToPortfolioInvestedCapital();
        setValueToPortfolioValuation();
        setValueToPortfolioReturnRate();
        setValueToPortfolioResult();

        setUserLabel();
        setTransactionPanelVisibility(false);

        logger.info("User table completed");

        logger.info("Run threads...");
        threadConfig.startThreadUpdate();
        logger.info("Threads have run");
    }

    private void setFontColor() {
        returnRate.setCellFactory(new TableCellColor());
        result.setCellFactory(new TableCellColor());
    }

    private void getUserInstruments() {
        String[] params = {"userId"};
        String[] values = {User.getUserInstance().getId()};
        InstrumentService service = new InstrumentService();
        service.sendGetRequest(ServiceConfig.INSTRUMENT_GET_ALL, params, values);
    }

    private void setValueToPortfolioInvestedCapital() {
        BigDecimal investedCapital = CalculationMap.getData().values().stream()
                .map(InstrumentCalculation::getInvestedCapital)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String investedCapitalString = editor.setSpacesWithCurrency(String.valueOf(investedCapital.setScale(2, 2)));
        invested_capital.setText(investedCapitalString);
        invested_capital.setStyle("-fx-font-weight: bold");
    }

    private void setValueToPortfolioValuation() {
        portfolio_valuation.setText(editor.setSpacesWithCurrency(String.valueOf(CalculationMap.calculateCurrentValuation())));
        portfolio_valuation.setStyle("-fx-font-weight: bold");
    }

    private void setValueToPortfolioReturnRate() {
        BigDecimal returnRate = CalculationMap.getData().values().stream()
                .map(t -> t.getReturnRate().multiply(t.getShareRatio()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return_rate.setText(editor.setRate(String.valueOf(returnRate.setScale(2, 2))));
        return_rate.setStyle("-fx-font-weight: bold");
        if(returnRate.doubleValue() < 0) {
            return_rate.setTextFill(Color.web("#d03400"));
        } else {
            return_rate.setTextFill(Color.web("#3bd000"));
        }
    }

    private void setValueToPortfolioResult() {
        BigDecimal returnRate = CalculationMap.getData().values().stream()
                .map(InstrumentCalculation::getResult)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        portfolio_result.setText(editor.setSpacesWithCurrency(String.valueOf(returnRate)));
        portfolio_result.setStyle("-fx-font-weight: bold");
        if(returnRate.doubleValue() < 0) {
            portfolio_result.setTextFill(Color.web("#d03400"));
        } else {
            portfolio_result.setTextFill(Color.web("#3bd000"));
        }
    }

    private void setTransactionPanelVisibility(boolean visible) {
        if (sharesList != null)
            sharesList.dispose();
        labelAction.setVisible(visible);
        confirmAction.setVisible(visible);
        cancelAction.setVisible(visible);
        instrumentAction.setText("");
        instrumentAction.setVisible(visible);
        instrumentAction.setManaged(true);
        quantityAction.setText("");
        quantityAction.setVisible(visible);
        quantityAction.setManaged(true);
        priceAction.setText("");
        priceAction.setVisible(visible);
        priceAction.setManaged(true);
    }

    private void setUserLabel() {
        userLabel.setText("User: " + User.getUserInstance().getUserName());
    }

    public void deleteAccount() {
        Confirmation confirmation = new Confirmation(Confirmation.DELETE);
        boolean isDeleted = confirmation.showConfirmation();
        if (isDeleted)
            try {
                threadConfig.interruptThreadUpdate();
                User.logOutOfUser();
                RecordList.clearList();
                FirstScene firstScene = new FirstScene();
                firstScene.createFirstWindow(GuiStage.GUI_STAGE);
            } catch (IOException ioe) {
                logger.error("Can not log out, system error");
                System.exit(0);
            }
    }

    public void buyButtonAction() {
        labelAction.setText("Buy instrument");
        confirmAction.setText("Buy");
        setTransactionPanelVisibility(true);
        Set<String> buyList = QuotationsMap.getData().keySet();
        sharesList = TextFields.bindAutoCompletion(instrumentAction, buyList);
    }

    public void sellButtonAction() {
        labelAction.setText("Sell instrument");
        confirmAction.setText("Sell");
        setTransactionPanelVisibility(true);
        Set<String> sellList = CalculationMap.getData().values().stream()
                .map(InstrumentCalculation::getName)
                .collect(Collectors.toSet());
        sharesList = TextFields.bindAutoCompletion(instrumentAction, sellList);
    }

    public void cancelTransactionAction() {
        setTransactionPanelVisibility(false);
    }

    public void confirmTransactionAction() {
        Information information;
        UserOperation userOperation = new UserOperation();
        String instrument = instrumentAction.getText();
        String quantity = quantityAction.getText();
        String price = priceAction.getText();
        String label = labelAction.getText();
        switch (label) {
            case "Buy instrument":
                boolean isBought = userOperation.buyShare(instrument, quantity, price);
                if(isBought) {
                    refreshUserPanel();
                    setTransactionPanelVisibility(true);
                    information = new Information(Information.BUY);
                    information.showInformation();
                    setTransactionPanelVisibility(false);
                }
                break;
            case "Sell instrument":
                boolean wasSold = userOperation.sellShare(instrument, quantity, price);
                if(wasSold) {
                    setFontColor();
                    refreshUserPanel();
                    setTransactionPanelVisibility(true);
                    information = new Information(Information.SELL);
                    information.showInformation();
                    setTransactionPanelVisibility(false);
                }
                break;
        }
    }

    public void rebuildUserTable() {
        logger.info("Start rebuilding table");
        data = FXCollections.observableArrayList(RecordList.getRecordList());
        userInstruments.setItems(data);
        userInstruments.getSortOrder().add(name);
        logger.info("Recounting parameters");
        setValueToPortfolioInvestedCapital();
        setValueToPortfolioValuation();
        setValueToPortfolioReturnRate();
        setValueToPortfolioResult();
    }

    public void setLogOutButtonAction() {
        instrumentAction.setVisible(false);
        instrumentAction.setManaged(false);
        quantityAction.setVisible(false);
        quantityAction.setManaged(false);
        priceAction.setVisible(false);
        priceAction.setManaged(false);
        labelAction.setText("Are you sure ?");
        labelAction.setVisible(true);
        confirmAction.setText("Exit");
        confirmAction.setVisible(true);
        cancelAction.setText("Cancel");
        cancelAction.setVisible(true);
        confirmAction.setOnAction(t -> {
            try {
                threadConfig.interruptThreadUpdate();
                User.logOutOfUser();
                RecordList.clearList();
                FirstScene firstScene = new FirstScene();
                firstScene.createFirstWindow(GuiStage.GUI_STAGE);
            } catch (IOException ioe) {
                logger.error("Can not log out, system error");
                System.exit(0);
            }
        });
    }

    public void setQuotationsButtonAction() {
        try {
            if(!Status.isQuotationsStageStatus()) {
                QuotationsScene quotationsScene = QuotationsScene.getInstance();
                quotationsScene.openQuotationsStage();
            } else {
                logger.info("The quotations stage has already been opened");
            }
        } catch (IOException ioe) {
            logger.error("Can open quotations table, system error");
        }
    }

    public void showStats() {}

    public void refreshUserPanel() {
        CalculationMap.getData().clear();
        RecordList.clearList();
        getUserInstruments();
        CalculationMap.setUserInstrumentPrice();
        CalculationMap.calculateShareRatios();
        rebuildUserTable();
        logger.info("User table refreshed, all is up to date");
    }

}
