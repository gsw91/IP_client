package com.gui.mapper;

import com.gui.domain.StatisticsRecord;
import com.gui.dto.StatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class StatisticsMapper {

    public List<StatisticsRecord> mapToRecordList(List<StatisticsDto> dtoList) {
        List<StatisticsRecord> recordList = new ArrayList<>();
        for (StatisticsDto statisticsDto: dtoList)
            recordList.add(mapToRecord(statisticsDto));
        return recordList;
    }

    private StatisticsRecord mapToRecord(StatisticsDto statisticsDto) {
        return new StatisticsRecord(
                statisticsDto.getInstrumentName(),
                String.valueOf(statisticsDto.getBuyingPrice()),
                statisticsDto.getBuyingDate(),
                String.valueOf(statisticsDto.getQuantity()),
                String.valueOf(statisticsDto.getSellingPrice()),
                statisticsDto.getSellingDate(),
                String.valueOf(statisticsDto.getResult()),
                String.valueOf(statisticsDto.getReturnRate()),
                String.valueOf(statisticsDto.getDuration()));
    }

}
