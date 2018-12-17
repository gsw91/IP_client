package com.gui.request;

import com.gui.config.ServiceConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface RequestCreator {

    default String sendGetRequest(String url, String requestMethod) throws IOException {
        URL urlPath = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
        connection.setRequestMethod(requestMethod);
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

    default String generateUrlWithJSON(String endpoint, String JSON) {
        return ServiceConfig.SERVER_URL + endpoint + JSON;
    }

}