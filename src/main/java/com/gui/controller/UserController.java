package com.gui.controller;

import com.google.gson.Gson;
import com.gui.config.ServiceConfig;
import com.gui.dto.UserDto;
import com.gui.request.RequestCreator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserController implements RequestCreator {

    private Logger logger = Logger.getLogger(UserController.class);

    public String signIn(String username, String password) {
        String endpoint = ServiceConfig.USER_SIGN_IN;
        String[] params = {"name", "password"};
        String[] values = {username, password};
        String url = generateUrlWithParams(endpoint, params, values);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            String output = readResponse(connection);
            connection.disconnect();
            return output;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            return CONNECTION_ERROR;
        }
    }

    public String deleteAccount(String userId) {
        String endpoint = ServiceConfig.USER_DELETE;
        String[] params = {"userIf"};
        String[] values = {userId};
        String url = generateUrlWithParams(endpoint, params, values);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            String output = readResponse(connection);
            connection.disconnect();
            return output;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            return CONNECTION_ERROR;
        }
    }

    public String sendRemindEmailRequest(String email) {
        String endpoint = ServiceConfig.USER_CHECK_MAIL;
        String[] params = {"mail"};
        String[] values = {email};
        String url = generateUrlWithParams(endpoint, params, values);
        try {
            URL urlPath = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            String output = readResponse(connection);
            connection.disconnect();
            return output;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            return CONNECTION_ERROR;
        }
    }

    public String createAccount(UserDto userDto) {
        logger.info("Creating account...");
        String endpoint = ServiceConfig.USER_CREATE;
        String url = generateUrl(endpoint);
        logger.info("Url: " + url);
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        logger.info("Json: " + json);
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
            logger.info("Response: " + output);
            connection.disconnect();
            return output;
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
            return CONNECTION_ERROR;
        }
    }

}
