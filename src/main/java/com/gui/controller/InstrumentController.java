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

    //buy share request
    public void addShare(InstrumentDto instrumentDto) {
        String endpoint = ServiceConfig.INSTRUMENT_BUY;
        String json = gson.toJson(instrumentDto);
        String url = generateUrl(endpoint);
        try {
            logger.info("Buying new instrument...");
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            logger.info("Setting properties of connection...");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            logger.info("Sending json in request...");
            logger.info(json);
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            logger.info("Json has been sent");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output = br.readLine();
            logger.info("The share was added: " + output.contains(instrumentDto.getSharesIndex()));
            br.close();
            connection.disconnect();
        } catch (IOException ioe) {
            logger.error("Something gone wrong " + ioe);
        }
    }

    //sell all shares
    public void sellShare(String shareName, String quantity, String price) {
        logger.info("Selling share " + shareName + "...");
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
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.close();
            connection.getInputStream();
            connection.disconnect();
        } catch (IOException ioe) {
            logger.error("Something gone wrong " + ioe);
        }
    }

}
