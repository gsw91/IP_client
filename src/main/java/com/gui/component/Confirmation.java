package com.gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

public class Confirmation {

    public final static String SURE = "Are you sure ?";

    private Logger logger = Logger.getLogger(Confirmation.class);
    private String message;

    public Confirmation(String message) {
        this.message = message;
    }

    public void showConfirmation() {
        logger.info(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.NO) {
            alert.close();
        } else if (alert.getResult() == ButtonType.YES) {

            System.out.println("Deleting the account!");

        }
    }


}
