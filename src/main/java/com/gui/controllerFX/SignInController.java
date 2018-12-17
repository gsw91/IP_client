package com.gui.controllerFX;

import com.gui.config.ServiceConfig;
import com.gui.config.GuiStage;
import com.gui.config.Status;
import com.gui.scene.UserStage;
import com.gui.service.FirstSceneRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SignInController {

    private Logger logger = Logger.getLogger(SignInController.class);

    @FXML
    private TextField loginTF;

    @FXML
    private TextField loginRemTF;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private Label warningLabel;

    private FirstSceneRequest request = new FirstSceneRequest();

    public void exit() {
        System.exit(0);
    }

    public void logIn() throws IOException {
        String login = loginTF.getText();
        String password = passwordPF.getText();
        String[] params = {"name", "password"};
        String[] values = {login, password};
        boolean isLogged = request.sendGetRequest(ServiceConfig.USER_SIGN_IN, params, values);
        if(Status.getServerStatus() && isLogged) {
            UserStage userStage = UserStage.getInstance();
            userStage.setUserScene(GuiStage.GUI_STAGE);
            logger.info("User signed in successfully");
        } else if (Status.getServerStatus()){
            showWarning("Wrong login or password");
            logger.warn("User sign in failed");
        } else {
            showWarning("The server is temporarily unavailable");
            logger.warn("User sign in failed");
        }
    }

    public void signUp() {
        showWarning("Connector to server lost");
    }

    public void remindPassword() {
        showWarning("Connector to server lost");
    }

    private void showWarning(String warning) {
        warningLabel.setText(warning);
        warningLabel.setVisible(true);
    }

}
