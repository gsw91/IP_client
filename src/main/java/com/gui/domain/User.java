package com.gui.domain.simple;

import org.apache.log4j.Logger;

public final class User {

    private static Logger LOGGER = Logger.getLogger(User.class);

    private String id;
    private String userName;
    private String password;
    private String mail;
    private static User userInstance = null;

    private User(String id, String userName, String password, String mail) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.mail = mail;
    }

    public static void setUserInstance(String id, String userName, String password, String mail){
        if(userInstance == null) {
            userInstance = new User(id, userName, password, mail);
            LOGGER.info("User session has set");
        }
    }

    public static User getUserInstance() {
        return userInstance;
    }

    public static void logOutOfUser() {
        LOGGER.info("User session has ended");
        userInstance = null;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

}
