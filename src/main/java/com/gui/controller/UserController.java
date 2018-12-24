package com.gui.controller;

import com.gui.config.ServiceConfig;
import com.gui.request.RequestCreator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserController implements RequestCreator {

    private Logger logger = Logger.getLogger(UserController.class);

    public void sendRemindEmailRequest(String email) {
        String endpoint = ServiceConfig.USER_CHECK_MAIL;
        String[] params = {"mail"};
        String[] values = {email};
        String url = generateUrlWithParams(endpoint, params, values);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setRequestMethod("GET");

        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }


        logger.info("Generated url: " + url);
    }

}
