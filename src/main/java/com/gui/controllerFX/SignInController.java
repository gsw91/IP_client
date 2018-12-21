package com.gui.controllerFX;

import com.gui.config.GuiStage;
import com.gui.config.ServiceConfig;
import com.gui.config.Status;
import com.gui.scene.UserStage;
import com.gui.service.FirstSceneRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    private Logger logger = Logger.getLogger(SignInController.class);

    @FXML
    private TextField loginTF;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private RadioButton signInRB;

    @FXML
    private RadioButton signUpRB;

    @FXML
    private RadioButton remindPasswordRB;

    @FXML
    private Label warningLabel;

    @FXML
    private Button signInB;

    @FXML
    private Button signUpB;

    @FXML
    private Button remindPasswordB;

    private FirstSceneRequest firstSceneRequest = new FirstSceneRequest();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureComponents();
    }

    public void signInRBAction() {
        signUpRB.setSelected(false);
        remindPasswordRB.setSelected(false);
        configureComponents();
    }

    public void signUpRBAction() {
        signInRB.setSelected(false);
        remindPasswordRB.setSelected(false);
        configureComponents();
    }

    public void remindPasswordRBAction() {
        signUpRB.setSelected(false);
        signInRB.setSelected(false);
        configureComponents();
    }

    private void configureComponents() {
        if (signInRB.isSelected()) {
            signInB.setManaged(true);
            signInB.setVisible(true);
            signUpB.setManaged(false);
            signUpB.setVisible(false);
            remindPasswordB.setManaged(false);
            remindPasswordB.setVisible(false);
            loginTF.setManaged(true);
            loginTF.setVisible(true);
            passwordPF.setManaged(true);
            passwordPF.setVisible(true);
            emailTF.setManaged(false);
            emailTF.setVisible(false);
        } else if (signUpRB.isSelected()) {
            signInB.setVisible(false);
            signInB.setManaged(false);
            signUpB.setVisible(true);
            signUpB.setManaged(true);
            remindPasswordB.setVisible(false);
            remindPasswordB.setManaged(false);
            loginTF.setVisible(true);
            loginTF.setManaged(true);
            passwordPF.setVisible(true);
            passwordPF.setManaged(true);
            emailTF.setVisible(true);
            emailTF.setManaged(true);
        } else if (remindPasswordRB.isSelected()) {
            signInB.setVisible(false);
            signInB.setManaged(false);
            signUpB.setVisible(false);
            signUpB.setManaged(false);
            remindPasswordB.setVisible(true);
            remindPasswordB.setManaged(true);
            loginTF.setVisible(false);
            loginTF.setManaged(false);
            passwordPF.setVisible(false);
            passwordPF.setManaged(false);
            emailTF.setVisible(true);
            emailTF.setManaged(true);
        }
    }



    public void exit() {
        System.exit(0);
    }

    private void changeCursor(Cursor cursor) {
        GuiStage.GUI_STAGE.getScene().getRoot().setCursor(cursor);

    }

    public void setWaitCursor() {
        changeCursor(Cursor.WAIT);
        loginTF.setCursor(Cursor.WAIT);
        passwordPF.setCursor(Cursor.WAIT);
    }

    public void setDefaultCursor() {
        changeCursor(Cursor.HAND);
        loginTF.setCursor(Cursor.TEXT);
        passwordPF.setCursor(Cursor.TEXT);
    }

    public void logIn() throws IOException {
        String login = loginTF.getText();
        String password = passwordPF.getText();
        String[] params = {"name", "password"};
        String[] values = {login, password};
        boolean isLogged = firstSceneRequest.sendGetRequest(ServiceConfig.USER_SIGN_IN, params, values);
        if(Status.getServerStatus() && isLogged) {
            setDefaultCursor();
            UserStage userStage = UserStage.getInstance();
            userStage.setUserScene(GuiStage.GUI_STAGE);
            logger.info("User signed in successfully");
        } else if (Status.getServerStatus()){
            showWarning("Wrong login or password");
            logger.warn("User sign in failed");
            setDefaultCursor();
        } else {
            showWarning("The server is temporarily unavailable");
            logger.warn("User sign in failed");
            setDefaultCursor();
        }
    }

    public void signUp() throws InterruptedException {
        Thread.sleep(3000);
        showWarning("Connector to server lost");
        setDefaultCursor();
    }

    public void remindPassword() throws InterruptedException {
        String mail = emailTF.getText();
        firstSceneRequest.sendEmail(mail);
        showWarning("Connector to server lost");
        Thread.sleep(3000);
        setDefaultCursor();
    }

    private void showWarning(String warning) {
        warningLabel.setText(warning);
        warningLabel.setVisible(true);
    }

}
