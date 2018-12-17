package com.gui;

import com.gui.config.ServiceConfig;
import com.gui.scene.FirstScene;
import com.gui.config.GuiStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        new ServiceConfig();
        GuiStage.GUI_STAGE = primaryStage;
        FirstScene firstScene = new FirstScene();
        firstScene.createFirstWindow(primaryStage);
    }

}

