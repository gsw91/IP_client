package com.gui.service;

import com.gui.component.Warning;
import com.gui.controller.InstrumentController;
import com.gui.controller.UserController;
import com.gui.data.CalculationMap;
import com.gui.data.QuotationsMap;
import com.gui.domain.InstrumentCalculation;
import com.gui.domain.User;
import com.gui.dto.InstrumentDto;
import com.gui.dto.UserDto;
import com.gui.editor.Editor;
import com.gui.request.RequestCreator;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.time.LocalDate;

public class UserOperation {

    private InstrumentController instrumentController = new InstrumentController();
    private UserController userController = new UserController();
    private Logger logger = Logger.getLogger(UserOperation.class);

    private final static String CONNECTION_ERROR = "Connection error";
    private final static String TRUE = "true";
    private final static String FALSE = "false";

    public boolean deleteAccount() {
        String userId = User.getUserInstance().getId();
        String output = userController.deleteAccount(userId);
        return output.equals(RequestCreator.TRUE);
    }

    public boolean buyShare(String instrument, String quantity, String price) {
        InstrumentDto instrumentDto;
        Warning warning;
        instrument = instrument.toUpperCase();
        if (!QuotationsMap.getData().containsKey(instrument)) {
            warning = new Warning(Warning.NAME);
            warning.showWarning();
            return false;
        } else {
            try {
                Editor editor = new Editor();
                Long userId = Long.valueOf(User.getUserInstance().getId().toUpperCase());
                Long qty = Long.valueOf(quantity);
                double buyingPrice = Double.valueOf(editor.replaceComma(price));
                String currentDate = String.valueOf(LocalDate.now());
                instrumentDto = new InstrumentDto(userId, qty, instrument, buyingPrice, currentDate);
                logger.info("Buying operation received");
                logger.info("Instrument: " + instrumentDto);
                return instrumentController.addShare(instrumentDto);
            } catch (NumberFormatException nfe) {
                warning = new Warning(Warning.FORMAT);
                warning.showWarning();
                return false;
            }
        }
    }

    public boolean sellShare(String instrument, String quantity, String price) {
        Warning warning;
        String instrumentName = instrument.toUpperCase();
        boolean doesNotUserHave = CalculationMap.getData().values().stream()
                .noneMatch(t -> t.getName().equals(instrumentName));
        long totalInstrumentQty = CalculationMap.getData().values().stream()
                .filter(t -> t.getName().equals(instrument))
                .mapToLong(InstrumentCalculation::getQuantity)
                .sum();
        if (doesNotUserHave) {
            warning = new Warning(Warning.NAME);
            warning.showWarning();
            return false;
        } else {
            try {
                Editor editor = new Editor();
                long sellingQuantity = Long.valueOf(quantity);
                if (sellingQuantity > totalInstrumentQty) {
                    warning = new Warning(Warning.QUANTITY);
                    warning.showWarning();
                    return false;
                }
                price = editor.replaceComma(price);
                Double.valueOf(editor.replaceComma(price));
                logger.info("Selling operation received");
                return instrumentController.sellShare(instrumentName, quantity, price);
            } catch (NumberFormatException nfe) {
                warning = new Warning(Warning.FORMAT);
                warning.showWarning();
                return false;
            }
        }
    }

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

    public boolean signInUser(String login, String password) {
        logger.info("Creating connection to server");
        String response = userController.signIn(login, password);
        if(!checkIfUserExists(response)) {
            return checkIfUserExists(response);
        } else {
            createSessionUser(response);
            if (User.getUserInstance() != null) {
                logger.info("The setting of user panel");
                return true;
            } else {
                return false;
            }
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
        if(response.contains("Connection error"))
            return false;
        JSONObject jsonResponse = new JSONObject(response);
        String id = String.valueOf(jsonResponse.get("id"));
        return !id.equals("null");
    }

}
