package com.gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

public class Error {

    public static String SERVER = "Server unavailable";

    private Logger logger = Logger.getLogger(Error.class);
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public void showError() {
        logger.info(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.CLOSE) {
            alert.close();
        }
    }

}
