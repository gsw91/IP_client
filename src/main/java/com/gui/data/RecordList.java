package com.gui.data;

import com.gui.domain.InstrumentCalculation;
import com.gui.domain.InstrumentRecord;
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
