package com.gui.component;

import com.gui.domain.simple.User;
import com.gui.service.UserOperation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

public class Confirmation {

    public final static String DELETE = "Delete your account?";

    private Logger logger = Logger.getLogger(Confirmation.class);
    private String message;

    public Confirmation(String message) {
        this.message = message;
    }

    public boolean showConfirmation() {
        logger.info(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        switch (message) {
            case DELETE:
                if(alert.getResult() == ButtonType.NO) {
                    alert.close();
                } else if (alert.getResult() == ButtonType.YES) {
                    UserOperation userOperation = new UserOperation();
                    boolean isRemoved = userOperation.deleteAccount();
                    if(isRemoved)
                        logger.info("Account of user " + User.getUserInstance().getUserName() + " was deleted");
                    else
                        logger.info("Can not delete the account");
                        Error error = new Error(Error.SERVER);
                        error.showError();
                    alert.close();
                    return isRemoved;
                }
                break;
        }
        return false;
    }


}
