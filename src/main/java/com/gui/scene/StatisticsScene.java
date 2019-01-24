package com.gui.scene;

import com.gui.config.Status;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class StatisticsScene {

    private Logger logger = Logger.getLogger(StatisticsScene.class);

    private static StatisticsScene statisticsSceneInstance = null;

    private StatisticsScene() {}

    public static StatisticsScene getInstance() {
        if(statisticsSceneInstance == null)
            return statisticsSceneInstance = new StatisticsScene();
        else
            return statisticsSceneInstance;
    }

    public void openStatisticsStage() throws IOException {
        URL file = getClass().getClassLoader().getResource("statistics_scene.fxml");
        if (file != null) {
            Parent root = FXMLLoader.load(file);
            Stage statisticsStage = new Stage();
            statisticsStage.setTitle("Current quotations");
            statisticsStage.setScene(new Scene(root));
            statisticsStage.setResizable(false);
            Status.exitOnCloseStatistics(statisticsStage);
            statisticsStage.show();
            logger.info("Current statistics stage is ready");
            Status.setStatisticsStageStatus(true);
        } else {
            logger.error("System cannot find the fxml file");
        }
    }

}
