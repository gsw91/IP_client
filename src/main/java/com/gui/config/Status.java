package com.gui.config;

import javafx.stage.Stage;
import org.apache.log4j.Logger;

public final class Status {

    private static Logger LOGGER = Logger.getLogger(Status.class);

    private static boolean quotationsStageStatus = false;

    public static boolean isQuotationsStageStatus() {
        return quotationsStageStatus;
    }

    public static void setQuotationsStageStatus(boolean quotationsStageStatus) {
        Status.quotationsStageStatus = quotationsStageStatus;
        LOGGER.info("Quotations status changed on: " + quotationsStageStatus);
    }

    public static boolean getServerStatus() {
        Connector connector = new Connector();
        return connector.checkConnectionStatus(ServiceConfig.SERVER_URL);
    }

    public static void exitOnCloseQuotations(Stage stage) {
        stage.setOnCloseRequest(t -> {
            Status.setQuotationsStageStatus(false);
        });
    }

}
