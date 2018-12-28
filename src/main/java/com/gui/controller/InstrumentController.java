package com.gui.controller;

import com.google.gson.Gson;
import com.gui.config.ServiceConfig;
import com.gui.domain.simple.User;
import com.gui.dto.InstrumentDto;
import com.gui.request.RequestCreator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InstrumentController implements RequestCreator {

    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(InstrumentController.class);

    public boolean addShare(InstrumentDto instrumentDto) {
        logger.info("Buying a new instrument...");
        String endpoint = ServiceConfig.INSTRUMENT_BUY;
        String json = gson.toJson(instrumentDto);
        String url = generateUrl(endpoint);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            logger.info("Sending json in request...");
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            logger.info("Json has been sent");
            String output = readResponse(connection);
            logger.info("The share was added: " + output.contains(instrumentDto.getSharesIndex()));
            connection.disconnect();
            return output.contains(instrumentDto.getSharesIndex());
        } catch (IOException ioe) {
            logger.error("Something gone wrong " + ioe);
            return false;
        }
    }

    public boolean sellShare(String shareName, String quantity, String price) {
        logger.info("Selling the instrument " + shareName + "...");
        String endpoint = ServiceConfig.INSTRUMENT_SELL;
        String userId = User.getUserInstance().getId();
        String[] params = {"userId", "name", "quantity", "price"};
        String[] values = {userId, shareName, quantity, price};
        String url = generateUrlWithParams(endpoint, params, values);
        logger.info("Generated url: " + url);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("PUT");
            String response = readResponse(connection);
            connection.disconnect();
            return Boolean.valueOf(response);
        } catch (IOException ioe) {
            logger.error("Something gone wrong " + ioe);
            return false;
        }
    }

}
