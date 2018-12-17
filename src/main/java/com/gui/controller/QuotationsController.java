package com.gui.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.gui.config.ServiceConfig;
import com.gui.dto.ShareDto;
import com.gui.request.RequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuotationsController implements RequestCreator {

    private Logger logger = Logger.getLogger(QuotationsController.class);

    public String getCurrentQuotationsResponse() {
        String url = generateUrl(ServiceConfig.GET_ALL_SHARES);
        try {
            String response = sendGetRequest(url, RequestMethod.GET);
            if (response.length()>1000)
                logger.info("Shares received");
            else
                logger.warn("Shares quotations unavailable");
            return response;
        } catch (IOException ioe) {
            logger.error("Connector refused", ioe);
            return "";
        }
    }

    public Map<String, ShareDto> setQuotationsMap(String response) {
        if(response.equals(""))
            return new HashMap<>();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Gson gson = new Gson();
        Map<String, Object> objectsMap = gson.fromJson(response, mapType);

        Map<String, ShareDto> quotationsMap = new HashMap<>();
        for(String key: objectsMap.keySet()) {
            String data = String.valueOf(objectsMap.get(key));
            ShareDto shareDto = parseToShareDto(data);
            quotationsMap.put(shareDto.getIndex(), shareDto);
        }
        return quotationsMap;
    }

    protected ShareDto parseToShareDto(String data) {
        data = data.replace("{", "");
        data = data.replace("}", "");
        String[] firstArray = data.split(",");
        List<String> values = new ArrayList<>();
        for(String first: firstArray) {
            values.add(first.split("=")[1]);
        }
        return new ShareDto(values.get(0), Double.valueOf(values.get(1)), LocalDateTime.parse(values.get(2)), LocalDateTime.parse(values.get(3)));
    }

}
