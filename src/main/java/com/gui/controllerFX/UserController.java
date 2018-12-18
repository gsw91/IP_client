package com.gui.controllerFX;

import com.gui.config.ServiceConfig;
import com.gui.config.Status;
import com.gui.domain.CalculationMap;
import com.gui.domain.QuotationsMap;
import com.gui.domain.simple.InstrumentCalculation;
import com.gui.domain.simple.InstrumentRecord;
import com.gui.domain.simple.User;
import com.gui.domain.RecordList;
import com.gui.dto.InstrumentDto;
import com.gui.editor.Editor;
import com.gui.scene.FirstScene;
import com.gui.config.GuiStage;
import com.gui.scene.QuotationsScene;
import com.gui.service.InstrumentService;
import com.gui.service.ShareOperation;
import com.gui.update.ThreadConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {

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

    private void setTransactionPanelVisibility(boolean visible) {
        labelAction.setVisible(visible);
        confirmAction.setVisible(visible);
        cancelAction.setVisible(visible);
        instrumentAction.setVisible(visible);
        instrumentAction.setManaged(true);
        quantityAction.setVisible(visible);
        quantityAction.setManaged(true);
        priceAction.setVisible(visible);
        priceAction.setManaged(true);
    }

    private Logger logger = Logger.getLogger(UserController.class);
    private Editor editor = new Editor();
    private ObservableList<InstrumentRecord> data;
    private ThreadConfig threadConfig = new ThreadConfig(this);

    private void setUserLabel() {
        userLabel.setText("User: " + User.getUserInstance().getUserName());
    }

    public void buyButtonAction() {
        labelAction.setText("Buy instrument");
        confirmAction.setText("Buy");
        setTransactionPanelVisibility(true);
    }

    public void sellButtonAction() {
        labelAction.setText("Sell instrument");
        confirmAction.setText("Sell");
        setTransactionPanelVisibility(true);
    }

    public void cancelTransactionAction() {
        setTransactionPanelVisibility(false);
    }

    public void confirmTransactionAction() {
        if(labelAction.getText().equals("Buy instrument")) {
            InstrumentDto instrumentDto = null;
            String shareIndex = instrumentAction.getText();
            try {
                Editor editor = new Editor();
                Long userId = Long.valueOf(User.getUserInstance().getId());
                Long quantity = Long.valueOf(quantityAction.getText());
                double price = Double.valueOf(editor.replaceComma(priceAction.getText()));
                String currentDate = String.valueOf(LocalDate.now());
                instrumentDto = new InstrumentDto(userId, quantity, shareIndex, price, currentDate);
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
            if(QuotationsMap.getData().containsKey(shareIndex) && instrumentDto!=null) {
                ShareOperation shareOperation = new ShareOperation();
                shareOperation.buyShare(instrumentDto);
                rebuildUserTable();
            }
        }
    }

    public void rebuildUserTable() {
        logger.info("Start rebuilding table");
        data = FXCollections.observableArrayList(RecordList.getRecordList());
        userInstruments.setItems(data);
        logger.info("Recounting parameters");
        setValueToPortfolioInvestedCapital();
        setValueToPortfolioValuation();
        setValueToPortfolioReturnRate();
        setValueToPortfolioResult();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUserInstruments();
        RecordList.installCalculationList(CalculationMap.getData());
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

        setValueToPortfolioInvestedCapital();
        setValueToPortfolioValuation();
        setValueToPortfolioReturnRate();
        setValueToPortfolioResult();

        setUserLabel();
        setTransactionPanelVisibility(false);

        logger.info("User table completed");

        QuotationsMap.setCurrentQuotations();

        logger.info("Run threads...");
        threadConfig.startThreadUpdate();
        logger.info("Threads have run");
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
        CalculationMap.refreshUserInstrumentPrice();
        CalculationMap.calculateShareRatios();
        rebuildUserTable();
        logger.info("User table refreshed, all is up to date");
    }

}
