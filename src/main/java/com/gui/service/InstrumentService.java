package com.gui.service;

import com.gui.data.CalculationMap;
import com.gui.dto.InstrumentDto;
import com.gui.mapper.InstrumentMapper;
import com.gui.request.GetRequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstrumentService implements GetRequestCreator {

    private Logger logger = Logger.getLogger(InstrumentService.class);
    private InstrumentMapper mapper = new InstrumentMapper();

    @Override
    public boolean sendGetRequest(String endpoint, String[] params, String[] values) {
        logger.info("Loading user instruments");
        try {
            String url = generateUrlWithParams(endpoint, params, values);
            String response = getResponse(url, RequestMethod.GET);
            logger.info("Instruments: " + response);
            if(response.equals("")) {
                return false;
            } else {
                JSONArray jsonArray = new JSONArray(response);
                List<InstrumentDto> instruments = setUserInstrumentsList(jsonArray);

                for (InstrumentDto instrument : instruments) {
                    CalculationMap.setUserInstrumentPrice(mapper.mapToInstrumentCalculation(instrument));
                }
                CalculationMap.calculateShareRatios();
                logger.info("User instruments have been loaded, quantity: " + CalculationMap.getData().size());
                return true;
            }
        } catch (IOException ioe) {
            logger.warn(ioe.getMessage());
            return false;
        }
    }

    private List<InstrumentDto> setUserInstrumentsList(JSONArray jsonArray) {
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
