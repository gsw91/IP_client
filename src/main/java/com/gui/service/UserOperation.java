package com.gui.service;

import com.gui.component.Warning;
import com.gui.controller.InstrumentController;
import com.gui.domain.CalculationMap;
import com.gui.domain.QuotationsMap;
import com.gui.domain.simple.InstrumentCalculation;
import com.gui.domain.simple.User;
import com.gui.dto.InstrumentDto;
import com.gui.editor.Editor;
import org.apache.log4j.Logger;
import java.time.LocalDate;

public class UserOperation {

    private InstrumentController instrumentController = new InstrumentController();
    private Logger logger = Logger.getLogger(UserOperation.class);

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
                .mapToLong(InstrumentCalculation::getQuantity)
                .count();
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

}
