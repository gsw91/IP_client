package com.gui.scene;

import com.gui.config.GuiStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public final class FirstScene {

    private Logger logger = Logger.getLogger(FirstScene.class);

    public void createFirstWindow(Stage primaryStage) throws IOException {
        URL file = getClass().getClassLoader().getResource("sign_in_scene.fxml");
        if(file != null) {
            Parent root = FXMLLoader.load(file);
            primaryStage.setTitle("Investment Portfolio Client");
            primaryStage.setScene(new Scene(root));
            GuiStage.exitOnClose2(primaryStage);
            primaryStage.show();
            checkApplicationStage();
            logger.info("Application ready");
        } else {
            logger.error("System cannot find the fxml file");
        }
    }

    private void checkApplicationStage() {
        if (GuiStage.GUI_STAGE != null) {
            logger.info("Application first stage assigned");
        } else {
            logger.warn("Assigning of application stage failed");
        }
    }

}
