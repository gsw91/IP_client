package com.gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

public class Information {

    public final static String BUY = "The instrument has been bought.";
    public final static String SELL = "The instrument has been sold.";

    private Logger logger = Logger.getLogger(Information.class);
    private String message;

    public Information(String message) {
        this.message = message;
    }

    public void showInformation() {
        logger.info(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.CLOSE);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.CLOSE) {
            alert.close();
        }
    }


}
