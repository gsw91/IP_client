package com.gui.service;

import com.gui.controller.QuotationsController;
import com.gui.domain.simple.Share;
import com.gui.mapper.ShareMapper;

import java.util.Map;

public class QuotationsService {

    private QuotationsController quotationsController = new QuotationsController();
    private ShareMapper shareMapper = new ShareMapper();

    public Map<String, Share> createQuotationsMap() {
        String quotationsResponse = quotationsController.getCurrentQuotationsResponse();
        return shareMapper.mapToDomainMap(quotationsController.setQuotationsMap(quotationsResponse));
    }

}
