package com.gui.controller;

import com.google.gson.Gson;
import com.gui.config.ServiceConfig;
import com.gui.dto.InstrumentDto;
import com.gui.dto.ShareDto;
import com.gui.request.RequestCreator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InstrumentController implements RequestCreator {

    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(InstrumentController.class);

    //buy share request
    public void addShare(InstrumentDto instrumentDto) {
        logger.info("Start addShare method");
        String endpoint = ServiceConfig.INSTRUMENT_BUY;
        String json = gson.toJson(instrumentDto);
        logger.info("Json: " + json);
        String url = generateUrl(endpoint);
        logger.info("url: " + url);
        try {
            logger.info("Connecting to server...");
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            logger.info("Setting properties of connection...");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            logger.info("Sending json...");
            logger.info(json);
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
            logger.info("Json has been sent");
            logger.info("Reading response...");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                logger.info(output);
            }
            logger.info("Response has been read, closing connection");
            connection.disconnect();

        } catch (MalformedURLException e) {

            logger.error("Something gone wrong " + e);

        } catch (IOException ioe) {
            logger.error("Something gone wrong " + ioe);
        }

    }

    //update share request
        //sell not all
    public void updateShare(ShareDto shareDto) {

    }

    //sell all shares
    public void removeShare(ShareDto shareDto) {

    }

}
