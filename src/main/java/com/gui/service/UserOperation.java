package com.gui.service;

import com.gui.controller.InstrumentController;
import com.gui.dto.InstrumentDto;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class UserOperation {

    private InstrumentController instrumentController = new InstrumentController();
    private Logger logger = Logger.getLogger(UserOperation.class);

    public void buyShare(InstrumentDto instrumentDto) {
        logger.info("Buying operation received");
        instrumentController.addShare(instrumentDto);
    }

    public void sellShare(String shareName, long quantity, BigDecimal price) {
        logger.info("Selling operation received");
        String qty = String.valueOf(quantity);
        String sellingPrice = String.valueOf(price);
        instrumentController.sellShare(shareName, qty, sellingPrice);
    }

}
