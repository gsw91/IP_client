package com.gui.data;

import com.gui.domain.StatisticsRecord;
import com.gui.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

public final class StatisticsList {

    private final static List<StatisticsRecord> recordList = new ArrayList<>();

    public static List<StatisticsRecord> getRecordList() {
        return recordList;
    }

    public static void reloadList() {
        StatisticsService statisticsService = new StatisticsService();
        clearList();
        recordList.addAll(statisticsService.loadStatistics());
    }

    public static void clearList() {
        recordList.clear();
    }

}
