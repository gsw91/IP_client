package com.gui.data;

import com.gui.domain.Share;
import com.gui.service.QuotationsService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class QuotationsMap {

    private static Logger LOGGER = Logger.getLogger(QuotationsMap.class);
    private static Map<String, Share> data = new HashMap<>();

    public static Map<String, Share> getData() {
        return data;
    }

    public static void setCurrentQuotations() {
        LOGGER.info("Init current quotations service");
        QuotationsService quotationsService = new QuotationsService();
        Map<String, Share> map = quotationsService.createQuotationsMap();
        if (map == null || map.size() == 0) {
            LOGGER.error("Loading current quotations failed");
        } else {
            data.putAll(map);
            LOGGER.info("Current quotations up to date");
        }
    }

}
