package com.gui.update;

import com.gui.controllerFX.UserSceneController;
import com.gui.domain.CalculationMap;
import com.gui.domain.QuotationsMap;
import javafx.application.Platform;
import org.apache.log4j.Logger;

public class ThreadUpdate implements Runnable {

    private Logger logger = Logger.getLogger(ThreadUpdate.class);
    private UserSceneController userSceneController;

    protected ThreadUpdate(UserSceneController userSceneController) {
        this.userSceneController = userSceneController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000); // <- 1 min
                    Platform.runLater(() -> {
                        CalculationMap.setUserInstrumentPrice();
                        CalculationMap.calculateShareRatios();
                        QuotationsMap.setCurrentQuotations();
                        userSceneController.rebuildUserTable();
                        logger.info("All data to date");
                    });
            } catch (InterruptedException e) {
                return;
            }

        }
    }


}


