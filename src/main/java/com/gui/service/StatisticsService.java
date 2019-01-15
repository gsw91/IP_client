package com.gui.service;

import com.gui.controller.StatisticsController;
import com.gui.domain.StatisticsRecord;
import com.gui.domain.User;
import com.gui.dto.StatisticsDto;
import com.gui.mapper.StatisticsMapper;

import java.util.List;

public class StatisticsService {

    private StatisticsController statisticsController = new StatisticsController();
    private StatisticsMapper statisticsMapper = new StatisticsMapper();

    public List<StatisticsRecord> loadStatistics() {
        List<StatisticsDto> dtoList = statisticsController.getStatistics(User.getUserInstance().getId());
        return statisticsMapper.mapToRecordList(dtoList);
    }

}
