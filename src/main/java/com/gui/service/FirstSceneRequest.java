package com.gui.service;

import com.gui.config.Status;
import com.gui.domain.simple.User;
import com.gui.request.GetRequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;

public class FirstSceneRequest implements GetRequestCreator {

    private Logger logger = Logger.getLogger(FirstSceneRequest.class);

    @Override
    public boolean sendGetRequest(String endpoint, String[] params, String[] values) {
        logger.info("Creating connection to server");
        try {
            String url = generateUrlWithParams(endpoint, params, values);
            String response = getResponse(url, RequestMethod.GET);
            boolean isExists = checkIfUserExists(response);
            if (!isExists) return false;
            createSessionUser(response);
            if (User.getUserInstance() != null) {
                logger.info("The setting of user panel");
                return true;
            } else {
                return false;
            }
        } catch (IOException ioe) {
            logger.warn("Connecting to server failed");
            return false;
        } catch (Exception exc) {
            logger.warn("No such user exists");
            return false;
        }
    }

    private void createSessionUser(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String id = String.valueOf(jsonResponse.get("id"));
        String userName = String.valueOf(jsonResponse.get("login"));
        String password =  String.valueOf(jsonResponse.get("password"));
        String email =  String.valueOf(jsonResponse.get("email"));
        User.setUserInstance(id, userName, password, email);
    }

    private boolean checkIfUserExists(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String id = String.valueOf(jsonResponse.get("id"));
        return !id.equals("null");
    }

}
