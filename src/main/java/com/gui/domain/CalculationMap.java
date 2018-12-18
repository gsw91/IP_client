package com.gui.domain;

import com.gui.domain.simple.InstrumentCalculation;
import com.gui.service.SharePriceService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class CalculationMap {

    private static Logger LOGGER = Logger.getLogger(CalculationMap.class);
    private static Map<Long, InstrumentCalculation> data = new HashMap<>();

    public static Map<Long, InstrumentCalculation> getData() {
        return data;
    }

    public static void refreshUserInstrumentPrice(InstrumentCalculation instrumentCalculation) {
        SharePriceService sharePriceService = new SharePriceService();
        String[] values = {instrumentCalculation.getName()};
        BigDecimal currentPrice = sharePriceService.getSharePrice(values);
        instrumentCalculation.setCurrentPrice(currentPrice.setScale(2, 2));
        data.put(instrumentCalculation.getId(), instrumentCalculation);
    }

    public static BigDecimal calculateCurrentValuation() {
        return data.values().stream()
                .map(InstrumentCalculation::getCurrentValuation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void calculateShareRatio(Long id) {
        BigDecimal portfolioValuation = calculateCurrentValuation();
        BigDecimal shareValuation = data.get(id).getCurrentValuation();
        data.get(id).setShareRatio(
                shareValuation.divide(portfolioValuation, 4, 4)
        );
    }

    public static void refreshUserInstrumentPrice() {
        LOGGER.info("Start updating prices");
        for(InstrumentCalculation calculation: data.values()) {
            CalculationMap.refreshUserInstrumentPrice(calculation);
        }
        LOGGER.info("Prices up to date, recalculation of parameters");
    }

    public static void calculateShareRatios() {
        for(InstrumentCalculation calculation: data.values()) {
            CalculationMap.calculateShareRatio(calculation.getId());
        }
        RecordList.reloadList(data);
        LOGGER.info("Shares ratios up to date");
    }

}
