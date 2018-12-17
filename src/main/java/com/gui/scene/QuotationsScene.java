package com.gui.scene;

import com.gui.config.Status;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public final class QuotationsScene {

    private Logger logger = Logger.getLogger(UserStage.class);

    private static QuotationsScene quotationsSceneInstance = null;

    private QuotationsScene() {}

    public static QuotationsScene getInstance() {
        if(quotationsSceneInstance == null)
            return quotationsSceneInstance = new QuotationsScene();
        else
            return quotationsSceneInstance;
    }

    public void openQuotationsStage() throws IOException {
        URL file = getClass().getClassLoader().getResource("quotations_scene.fxml");
        if (file != null) {
            Parent root = FXMLLoader.load(file);
            Stage quotationsStage = new Stage();
            quotationsStage.setTitle("Current quotations");
            quotationsStage.setScene(new Scene(root));
            quotationsStage.setResizable(false);
            Status.exitOnCloseQuotations(quotationsStage);
            quotationsStage.show();
            logger.info("Current quotations stage is ready");
            Status.setQuotationsStageStatus(true);
        } else {
            logger.error("System cannot find the fxml file");
        }
    }

}
