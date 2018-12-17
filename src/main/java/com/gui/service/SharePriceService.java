package com.gui.service;

import com.gui.config.ServiceConfig;
import com.gui.dto.ShareDto;
import com.gui.request.GetRequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SharePriceService implements GetRequestCreator {

    private Logger logger = Logger.getLogger(SharePriceService.class);
    private BigDecimal sharePrice;

    public BigDecimal getSharePrice(String[] values) {
        String[] params = {"name"};
        sendGetRequest(ServiceConfig.GET_SHARE, params, values);
        return sharePrice;
    }

    @Override
    public boolean sendGetRequest(String endpoint, String[] params, String[] values) {
        logger.info("Loading user instrument: " + values[0]);
        try {
            String url = generateUrlWithParams(endpoint, params, values);
            String response = getResponse(url, RequestMethod.GET);
            JSONObject jsonObject = new JSONObject(response);
            ShareDto share = setSharesDto(jsonObject);
            if(share.getIndex() != null) {
                sharePrice = BigDecimal.valueOf(share.getCurrentPrice()).setScale(2, 2);
                logger.info("Current price for instrument " + share.getIndex() + " have been loaded");
                return true;
            }
            logger.info("Can not load current price for instrument");
            return false;
        } catch (IOException ioe) {
            logger.warn(ioe.getMessage());
            return false;
        }
    }

    private ShareDto setSharesDto(JSONObject jsonObject){
        return new ShareDto(
                String.valueOf(jsonObject.get("index")),
                Double.valueOf(String.valueOf(jsonObject.get("price"))),
                LocalDateTime.parse(String.valueOf(jsonObject.get("applicationActualization"))),
                LocalDateTime.parse(String.valueOf(jsonObject.get("serverActualization")))
        );
    }

}
