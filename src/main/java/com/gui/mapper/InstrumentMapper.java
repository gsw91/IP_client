package com.gui.mapper;

import com.gui.domain.simple.InstrumentCalculation;
import com.gui.domain.simple.InstrumentRecord;
import com.gui.dto.InstrumentDto;
import com.gui.editor.Editor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstrumentMapper {

    private Editor editor = new Editor();

    public List<InstrumentRecord> mapToRecordList(Map<Long, InstrumentCalculation> calculationMap) {
        List<InstrumentRecord> recordList = new ArrayList<>();
        for(InstrumentCalculation instrumentCalculation: calculationMap.values()) {
            recordList.add(mapToInstrumentRecord(instrumentCalculation));
        }
        return recordList;
    }

    public InstrumentRecord mapToInstrumentRecord(InstrumentCalculation instrumentCalculation) {
        return new InstrumentRecord(
                instrumentCalculation.getName(),
                editor.setSpacesToQuantity(String.valueOf(instrumentCalculation.getQuantity())),
                editor.setSpacesWithCurrency(String.valueOf(instrumentCalculation.getBuyingPrice())),
                editor.setSpacesWithCurrency(String.valueOf(instrumentCalculation.getInvestedCapital())),
                editor.setSpacesWithCurrency(String.valueOf(instrumentCalculation.getCurrentPrice())),
                editor.setSpacesWithCurrency(String.valueOf(instrumentCalculation.getCurrentValuation())),
                editor.setRate(String.valueOf(instrumentCalculation.getShareRatio().multiply(BigDecimal.valueOf(100)).setScale(2, 2))),
                editor.setRate(String.valueOf(instrumentCalculation.getReturnRate())),
                editor.setSpacesWithCurrency(String.valueOf(instrumentCalculation.getResult()))
        );
    }

    public InstrumentCalculation mapToInstrumentCalculation(InstrumentDto instrumentDto) {
        return new InstrumentCalculation(
                instrumentDto.getId(),
                instrumentDto.getSharesIndex(),
                instrumentDto.getQuantity(),
                BigDecimal.valueOf(instrumentDto.getBuyingPrice())
        );
    }

}
