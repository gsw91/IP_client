package com.gui.service;

import com.gui.controller.UserController;
import com.gui.domain.User;
import com.gui.dto.UserDto;
import com.gui.request.GetRequestCreator;
import com.gui.request.RequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;

public class FirstSceneService implements GetRequestCreator, RequestCreator {

    private Logger logger = Logger.getLogger(FirstSceneService.class);
    private UserController userController = new UserController();

    public String[] createUser(String name, String password, String email) {
        if (name.length() < 4)
            return new String[]{FALSE, "The username length must be longer than 4 chars"};
        else if (password.length() < 4)
            return new String[]{FALSE, "The password length must be longer than 4 chars"};
        else if (!email.contains("@") || !email.contains(".")) {
            return new String[]{FALSE, "Incorrect email"};
        }
        logger.info("Creating new user...");
        UserDto userDto = new UserDto(name, password, email);
        String response = userController.createAccount(userDto);
        if (response.contains("User created"))
            return new String[]{TRUE, response};
        else
            return new String[]{FALSE, response};
    }

    public String[] sendEmail(String email) {
        if (email.length()<4)
            return new String[]{FALSE,"Email does not exist"};
        String response = userController.sendRemindEmailRequest(email);
        switch (response) {
            case FALSE:
                return new String[]{response, "Email does not exist"};
            case TRUE:
                return new String[]{response, "Email has been sent"};
            case CONNECTION_ERROR:
                return new String[]{FALSE, response};
            default:
                return new String[]{FALSE, response};
        }
    }

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
