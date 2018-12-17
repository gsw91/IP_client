package com.gui.service;

import com.gui.controller.InstrumentController;
import com.gui.dto.InstrumentDto;

public class ShareOperation {

    private InstrumentController instrumentController = new InstrumentController();

   //InstrumentDto(111L, 1000L, "PKNORLEN", 100.00, "2018-12-17");
    public void buyShare(InstrumentDto instrumentDto) {
        instrumentController.addShare(instrumentDto);
    }

}
