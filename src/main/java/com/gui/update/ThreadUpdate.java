package com.gui.update;

import com.gui.controllerFX.UserController;
import com.gui.domain.CalculationMap;
import com.gui.domain.QuotationsMap;
import javafx.application.Platform;
import org.apache.log4j.Logger;

public class ThreadUpdate implements Runnable {

    private Logger logger = Logger.getLogger(ThreadUpdate.class);
    private UserController userController;

    protected ThreadUpdate(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000); // <- 1 min
                    Platform.runLater(() -> {
                        CalculationMap.refreshUserInstrumentPrice();
                        CalculationMap.calculateShareRatios();
                        QuotationsMap.setCurrentQuotations();
                        userController.rebuildUserTable();
                        logger.info("All data to date");
                    });
            } catch (InterruptedException e) {
                return;
            }

        }
    }


}


