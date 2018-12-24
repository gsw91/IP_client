package com.gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

public class Warning {

    public final static String NAME = "The instrument name is incorrect!";
    public final static String FORMAT = "Incorrect values!";
    public final static String QUANTITY = "You do not have enough shares to sell!";

    private Logger logger = Logger.getLogger(Warning.class);
    private String message;

    public Warning(String message) {
        this.message = message;
    }

    public void showWarning() {
        logger.warn(message);
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.CLOSE);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.CLOSE) {
            alert.close();
        }
    }

}
