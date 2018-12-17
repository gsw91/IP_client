package com.gui.config;

import javafx.application.Platform;
import javafx.stage.Stage;

public final class GuiStage {

    public static Stage GUI_STAGE = null;

      public static void exitOnClose2(Stage stage) {
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
