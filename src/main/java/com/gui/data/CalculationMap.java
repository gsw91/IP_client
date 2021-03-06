package com.gui.data;

import com.gui.domain.InstrumentCalculation;
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

    public static void setUserInstrumentPrice(InstrumentCalculation instrumentCalculation) {
        BigDecimal currentPrice = QuotationsMap.getData().get(instrumentCalculation.getName()).getCurrentPrice();
        instrumentCalculation.setCurrentPrice(currentPrice.setScale(2, 2));
        data.put(instrumentCalculation.getId(), instrumentCalculation);
    }

    public static BigDecimal calculateCurrentValuation() {
        return data.values().stream()
                .map(InstrumentCalculation::getCurrentValuation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static void setUserInstrumentPrice() {
        LOGGER.info("Start updating prices");
        for(InstrumentCalculation calculation: data.values()) {
            CalculationMap.setUserInstrumentPrice(calculation);
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

    private static void calculateShareRatio(Long id) {
        BigDecimal portfolioValuation = calculateCurrentValuation();
        BigDecimal shareValuation = data.get(id).getCurrentValuation();
        data.get(id).setShareRatio(
                shareValuation.divide(portfolioValuation, 4, 4)
        );
    }

}
