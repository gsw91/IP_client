package com.gui.controllerFX;

import com.gui.config.GuiStage;
import com.gui.config.ServiceConfig;
import com.gui.config.Status;
import com.gui.scene.UserStage;
import com.gui.service.FirstSceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInSceneController implements Initializable {

    private Logger logger = Logger.getLogger(SignInSceneController.class);

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
    private Label infoLabel;

    @FXML
    private Button signInB;

    @FXML
    private Button signUpB;

    @FXML
    private Button remindPasswordB;

    private FirstSceneService firstSceneService = new FirstSceneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureComponents();
    }

    public void signInRBAction() {
        signUpRB.setSelected(false);
        remindPasswordRB.setSelected(false);
        configureComponents();
        setDefaultCursor();
    }

    public void signUpRBAction() {
        signInRB.setSelected(false);
        remindPasswordRB.setSelected(false);
        configureComponents();
        setDefaultCursor();
    }

    public void remindPasswordRBAction() {
        signUpRB.setSelected(false);
        signInRB.setSelected(false);
        configureComponents();
        setDefaultCursor();
    }

    private void configureComponents() {
        if (signInRB.isSelected()) {
            signInB.setManaged(true);
            signInB.setVisible(true);
            signUpB.setManaged(false);
            signUpB.setVisible(false);
            remindPasswordB.setManaged(false);
            remindPasswordB.setVisible(false);
            loginTF.setText("");
            loginTF.setManaged(true);
            loginTF.setVisible(true);
            passwordPF.setText("");
            passwordPF.setManaged(true);
            passwordPF.setVisible(true);
            emailTF.setText("");
            emailTF.setManaged(false);
            emailTF.setVisible(false);
        } else if (signUpRB.isSelected()) {
            signInB.setVisible(false);
            signInB.setManaged(false);
            signUpB.setVisible(true);
            signUpB.setManaged(true);
            remindPasswordB.setVisible(false);
            remindPasswordB.setManaged(false);
            loginTF.setText("");
            loginTF.setVisible(true);
            loginTF.setManaged(true);
            passwordPF.setText("");
            passwordPF.setVisible(true);
            passwordPF.setManaged(true);
            emailTF.setText("");
            emailTF.setVisible(true);
            emailTF.setManaged(true);
        } else if (remindPasswordRB.isSelected()) {
            signInB.setVisible(false);
            signInB.setManaged(false);
            signUpB.setVisible(false);
            signUpB.setManaged(false);
            remindPasswordB.setVisible(true);
            remindPasswordB.setManaged(true);
            loginTF.setText("");
            loginTF.setVisible(false);
            loginTF.setManaged(false);
            passwordPF.setText("");
            passwordPF.setVisible(false);
            passwordPF.setManaged(false);
            emailTF.setText("");
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

    private void setDefaultCursor() {
        changeCursor(Cursor.HAND);
        loginTF.setCursor(Cursor.TEXT);
        passwordPF.setCursor(Cursor.TEXT);
    }

    public void logIn() throws IOException {
        String login = loginTF.getText();
        String password = passwordPF.getText();
        String[] params = {"name", "password"};
        String[] values = {login, password};
        boolean isLogged = firstSceneService.sendGetRequest(ServiceConfig.USER_SIGN_IN, params, values);
        if(Status.getServerStatus() && isLogged) {
            setDefaultCursor();
            UserStage userStage = UserStage.getInstance();
            userStage.setUserScene(GuiStage.GUI_STAGE);
            logger.info("User signed in successfully");
        } else if (Status.getServerStatus()){
            showInfoLabel("Wrong login or password", Color.RED);
            logger.warn("User sign in failed");
            setDefaultCursor();
        } else {
            showInfoLabel("The server is temporarily unavailable", Color.RED);
            logger.warn("User sign in failed");
            setDefaultCursor();
        }
    }

    public void signUp() {
        String name = loginTF.getText();
        String password = passwordPF.getText();
        String email = emailTF.getText();
        String[] response = firstSceneService.createUser(name, password, email);
        boolean isCreated = Boolean.valueOf(response[0]);
        if (isCreated) {
            showInfoLabel(response[1], Color.GREEN);
        } else {
            showInfoLabel(response[1], Color.RED);
        }
        setDefaultCursor();
    }

    public void remindPassword() {
        String mail = emailTF.getText();
        String[] response = firstSceneService.sendEmail(mail);
        boolean isCreated = Boolean.valueOf(response[0]);
        if (isCreated) {
            showInfoLabel(response[1], Color.GREEN);
        } else {
            showInfoLabel(response[1], Color.RED);
        }
        setDefaultCursor();
    }

    private void showInfoLabel(String warning, Color color) {
        infoLabel.setText(warning);
        infoLabel.setTextFill(color);
        infoLabel.setVisible(true);
    }

}
