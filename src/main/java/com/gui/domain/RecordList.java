package com.gui.domain;

import com.gui.domain.simple.InstrumentCalculation;
import com.gui.domain.simple.InstrumentRecord;
import com.gui.mapper.InstrumentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RecordList {

    private final static List<InstrumentRecord> recordList = new ArrayList<>();

    public static List<InstrumentRecord> getRecordList() {
        return recordList;
    }

    public static void reloadList(Map<Long, InstrumentCalculation> calculationMap) {
        InstrumentMapper mapper = new InstrumentMapper();
        clearList();
        recordList.addAll(mapper.mapToRecordList(calculationMap));
    }

    public static void clearList() {
        recordList.clear();
    }

}
