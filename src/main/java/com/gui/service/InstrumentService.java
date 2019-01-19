package com.gui.service;

import com.gui.controller.InstrumentController;
import com.gui.data.CalculationMap;
import com.gui.domain.User;
import com.gui.dto.InstrumentDto;
import com.gui.mapper.InstrumentMapper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstrumentService {

    private Logger logger = Logger.getLogger(InstrumentService.class);
    private InstrumentMapper mapper = new InstrumentMapper();
    private InstrumentController instrumentController = new InstrumentController();

    public void loadUserInstruments() {
        String response = instrumentController.getUserInstruments(User.getUserInstance().getId());
        List<InstrumentDto> dtoList = setUserInstrumentsList(response);
        for (InstrumentDto instrument : dtoList) {
            CalculationMap.setUserInstrumentPrice(mapper.mapToInstrumentCalculation(instrument));
        }
        CalculationMap.calculateShareRatios();
        logger.info("User instruments have been loaded, quantity: " + CalculationMap.getData().size());
    }

    private List<InstrumentDto> setUserInstrumentsList(String response) {
        JSONArray jsonArray = new JSONArray(response);
        List<InstrumentDto> instruments = new ArrayList<>();
        int objectsNumber = jsonArray.length();
        for(int i =0; i<objectsNumber; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            instruments.add(new InstrumentDto(
                    Long.valueOf(String.valueOf(jsonObject.get("id"))),
                    Long.valueOf(String.valueOf(jsonObject.get("userId"))),
                    Long.valueOf(String.valueOf(jsonObject.get("quantity"))),
                    String.valueOf(jsonObject.get("sharesIndex")),
                    Double.valueOf(String.valueOf(jsonObject.get("buyingPrice"))),
                    String.valueOf(jsonObject.get("buyingDate")))
            );
        }
        return instruments;
    }

}
