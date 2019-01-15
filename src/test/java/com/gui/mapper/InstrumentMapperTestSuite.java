package com.gui.mapper;

import com.gui.domain.InstrumentCalculation;
import com.gui.domain.InstrumentRecord;
import com.gui.dto.InstrumentDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class InstrumentMapperTestSuite {

    private InstrumentMapper mapper;

    @Before
    public void setVariables() {
        mapper = new InstrumentMapper();
    }

    @Test
    public void testMapToInstrumentCalculation() {
        //given
        InstrumentDto instrumentDto = new InstrumentDto(1L, 2L, 1000L, "KREZUS", 1.02d, "2018-11-04");
        //when
        InstrumentCalculation instrumentCalculation = mapper.mapToInstrumentCalculation(instrumentDto);
        instrumentCalculation.setCurrentPrice(BigDecimal.valueOf(1.1));
        instrumentCalculation.setShareRatio(BigDecimal.valueOf(0.5));
        //then
        Assert.assertEquals("KREZUS", instrumentCalculation.getName());
        Assert.assertEquals(1000L, instrumentCalculation.getQuantity());
        Assert.assertEquals(BigDecimal.valueOf(1.02).setScale(2, 2), instrumentCalculation.getBuyingPrice());
        Assert.assertEquals(BigDecimal.valueOf(1100).setScale(2, 2), instrumentCalculation.getCurrentValuation());
        Assert.assertEquals(BigDecimal.valueOf(1020).setScale(2, 2), instrumentCalculation.getInvestedCapital());
        Assert.assertEquals(BigDecimal.valueOf(1.1).setScale(2, 2), instrumentCalculation.getCurrentPrice());
        Assert.assertEquals(BigDecimal.valueOf(0.50).setScale(4, 4), instrumentCalculation.getShareRatio());
        Assert.assertEquals(BigDecimal.valueOf(80).setScale(2, 2), instrumentCalculation.getResult());
    }

    @Test
    public void testMapToInstrumentRecord() {
        //given
        InstrumentCalculation instrumentCalculation = new InstrumentCalculation(1L,"KREZUS", 1000L, BigDecimal.valueOf(1.02d));
        instrumentCalculation.setCurrentPrice(BigDecimal.valueOf(1.10d));
        instrumentCalculation.setShareRatio(BigDecimal.valueOf(0.5));
        //when
        InstrumentRecord instrumentRecord = mapper.mapToInstrumentRecord(instrumentCalculation);
        //then
        Assert.assertEquals("KREZUS", instrumentRecord.getName());
        Assert.assertEquals("1 000", instrumentRecord.getQuantity());
        Assert.assertEquals("1,02 zł", instrumentRecord.getBuyingPrice());
        Assert.assertEquals("1 100,00 zł", instrumentRecord.getCurrentValuation());
        Assert.assertEquals("1 020,00 zł", instrumentRecord.getInvestedCapital());
        Assert.assertEquals("1,10 zł", instrumentRecord.getCurrentPrice());
        Assert.assertEquals("50,00 %", instrumentRecord.getShareRatio());
        Assert.assertEquals("80,00 zł", instrumentRecord.getResult());
    }

    @Test
    public void testMapToRecordMap() {
        //given
        InstrumentCalculation instrumentCalculationOne = new InstrumentCalculation(1L,"KREZUS", 1000L, BigDecimal.valueOf(2.22d));
        instrumentCalculationOne.setCurrentPrice(BigDecimal.valueOf(0.88));
        instrumentCalculationOne.setShareRatio(BigDecimal.valueOf(0.5));
        InstrumentCalculation instrumentCalculationTwo = new InstrumentCalculation(2L,"COGNOR", 1000L, BigDecimal.valueOf(1.81d));
        instrumentCalculationTwo.setCurrentPrice(BigDecimal.valueOf(1.98));
        instrumentCalculationTwo.setShareRatio(BigDecimal.valueOf(0.3311));
        InstrumentCalculation instrumentCalculationThree = new InstrumentCalculation(3L,"POLIMEXMS", 1000L, BigDecimal.valueOf(4.65d));
        instrumentCalculationThree.setCurrentPrice(BigDecimal.valueOf(3.000));
        instrumentCalculationThree.setShareRatio(BigDecimal.valueOf(0.1699));

        Map<Long, InstrumentCalculation> calculationMap = new HashMap<>();
        calculationMap.put(instrumentCalculationOne.getId(), instrumentCalculationOne);
        calculationMap.put(instrumentCalculationTwo.getId(), instrumentCalculationTwo);
        calculationMap.put(instrumentCalculationThree.getId(), instrumentCalculationThree);
        //when
        List<InstrumentRecord> recordList = mapper.mapToRecordList(calculationMap);
        //then
        Assert.assertEquals(3, recordList.size());
        Assert.assertEquals("50,00 %", recordList.get(0).getShareRatio());
        Assert.assertEquals("33,11 %", recordList.get(1).getShareRatio());
        Assert.assertEquals("16,99 %", recordList.get(2).getShareRatio());
    }

}
