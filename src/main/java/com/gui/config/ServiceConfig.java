package com.gui.config;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class ServiceConfig {

    private static Logger logger = Logger.getLogger(ServiceConfig.class);
    private static File endpoints = new File("endpoints.properties");

    //server endpoint
    public final static String SERVER_URL = getPropertyValue(endpoints, "address.service.base");
    //user controller endpoints
    public final static String USER_SIGN_IN = getPropertyValue(endpoints, "address.service.user.login");
    public final static String USER_CHECK_MAIL = getPropertyValue(endpoints, "address.service.user.checkMail");
    public final static String USER_CREATE = getPropertyValue(endpoints, "address.service.user.create");
    //instrument controller endpoints
    public final static String INSTRUMENT_BUY = getPropertyValue(endpoints, "address.service.instrument.buy");
    public final static String INSTRUMENT_GET_ALL = getPropertyValue(endpoints, "address.service.instrument.show");
    public final static String INSTRUMENT_SELL = getPropertyValue(endpoints, "address.service.instrument.sell");
    //share controller endpoints
    public final static String GET_ALL_SHARES = getPropertyValue(endpoints, "address.service.share.all");
    public final static String GET_SHARE = getPropertyValue(endpoints, "address.service.share.getOne");

    private static String getPropertyValue(File file, String property) {
        Properties properties = new Properties();
        if (!file.canRead()) {
            file = new File("src\\main\\resources\\endpoints.properties");
        }
        try {
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
            logger.info("Property for " + property + " assigned");
            return properties.getProperty(property);
        } catch (IOException ioe) {
            logger.error("File not found");
            return "";
        }
    }

}
