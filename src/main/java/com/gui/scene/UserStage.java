package com.gui.scene;

import com.gui.config.GuiStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public final class UserStage {

    private Logger logger = Logger.getLogger(UserStage.class);
    private static UserStage userStageInstance = null;

    private UserStage() {}

    public static UserStage getInstance() {
        if (userStageInstance == null)
            return userStageInstance = new UserStage();
        else
            return userStageInstance;
    }

    public void setUserScene(Stage primaryStage) throws IOException {
        URL file = getClass().getClassLoader().getResource("user_panel_scene.fxml");
        if (file != null) {
            Parent root = FXMLLoader.load(file);
            primaryStage.setTitle("Investment Portfolio Client");
            primaryStage.setScene(new Scene(root, 940, 560));
            primaryStage.setResizable(false);
            GuiStage.exitOnClose2(primaryStage);
            primaryStage.show();
            logger.info("User panel is ready");
        } else {
            logger.error("System cannot find the fxml file");
        }
    }

}
