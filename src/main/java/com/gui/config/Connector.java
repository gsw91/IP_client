package com.gui.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Connector {

    private Logger logger = Logger.getLogger(Connector.class);

    protected boolean checkConnectionStatus(String serverUrl) {
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();
            if (code == 200)
                logger.info("Server is available");
                return true;
        } catch (IOException e) {
            logger.error("Server is unavailable");
            return false;
        }
    }

}
