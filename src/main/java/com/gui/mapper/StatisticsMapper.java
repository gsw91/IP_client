package com.gui.mapper;

import com.gui.domain.StatisticsRecord;
import com.gui.dto.StatisticsDto;
import com.gui.editor.Editor;

import java.util.ArrayList;
import java.util.List;

public class StatisticsMapper {

    private Editor editor = new Editor();

    public List<StatisticsRecord> mapToRecordList(List<StatisticsDto> dtoList) {
        List<StatisticsRecord> recordList = new ArrayList<>();
        for (StatisticsDto statisticsDto: dtoList)
            recordList.add(mapToRecord(statisticsDto));
        return recordList;
    }

    private StatisticsRecord mapToRecord(StatisticsDto statisticsDto) {
        return new StatisticsRecord(
                statisticsDto.getInstrumentName(),
                editor.setSpacesWithCurrency(String.valueOf(statisticsDto.getBuyingPrice())),
                statisticsDto.getBuyingDate(),
                editor.setSpacesToQuantity(String.valueOf(statisticsDto.getQuantity())),
                editor.setSpacesWithCurrency(String.valueOf(statisticsDto.getSellingPrice())),
                statisticsDto.getSellingDate(),
                editor.setSpacesWithCurrency(String.valueOf(statisticsDto.getResult())),
                editor.setRate(String.valueOf(statisticsDto.getReturnRate())),
                editor.setSpacesToQuantity(String.valueOf(statisticsDto.getDuration())));
    }

}
