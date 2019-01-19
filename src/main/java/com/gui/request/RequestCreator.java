package com.gui.request;

import com.gui.config.ServiceConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface RequestCreator {

    String CONNECTION_ERROR = "Connection error";
    String TRUE = "true";
    String FALSE = "false";

    default String sendGetRequest(String url) throws IOException {
        URL urlPath = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
        return "";
    }

    default String generateUrlWithParams(String endpoint, String[] params, String[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceConfig.SERVER_URL);
        stringBuilder.append(endpoint);
        stringBuilder.append("?");

        if (params.length != values.length) {
            return "";
        } else {
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(params[i]);
                stringBuilder.append("=");
                stringBuilder.append(values[i]);
            }
        }
        return stringBuilder.toString();
    }

    default String generateUrl(String endpoint) {
        return ServiceConfig.SERVER_URL + endpoint;
    }

    default String readResponse(HttpURLConnection connection) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

}
