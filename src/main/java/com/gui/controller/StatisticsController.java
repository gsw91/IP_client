package com.gui.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gui.config.ServiceConfig;
import com.gui.domain.User;
import com.gui.dto.StatisticsDto;
import com.gui.request.RequestCreator;
import com.gui.request.RequestMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatisticsController implements RequestCreator {

    private Logger logger = Logger.getLogger(StatisticsController.class);

    public List<StatisticsDto> getStatistics(String userId) {
        String[] params = {"userId"};
        String[] values = {userId};
        String url = generateUrlWithParams(ServiceConfig.SHOW_STATS, params, values);
        try {
            String response = sendGetRequest(url, RequestMethod.GET);
            Type listType = new TypeToken<List<Object>>(){}.getType();
            Gson gson = new Gson();
            List<Object> objectsList = gson.fromJson(response, listType);
            List<StatisticsDto> dtoList = new ArrayList<>();
            logger.info("Loading statistics for user: " + User.getUserInstance().getId());
            for(Object o: objectsList) {
                StatisticsDto statisticsDto = parseToStatisticsDto(o);
                dtoList.add(statisticsDto);
            }
            if(dtoList.size()>0)
                logger.info("Statistics ready");
            else
                logger.warn("No statistics of current user");
            return dtoList;
        } catch (IOException ioe ) {
            logger.error("Connection error");
        }
        return new ArrayList<>();
    }

    public boolean resetStatistics(Long userId)  {
        return false;
    }

    protected StatisticsDto parseToStatisticsDto(Object o) {
        String stat = String.valueOf(o);
        stat = stat.replace("{", "");
        stat = stat.replace("}", "");
        stat = stat.replace(".0", "");
        String[] firstArray = stat.split(",");
        List<String> values = new ArrayList<>();
        for (String first : firstArray) {
            values.add(first.split("=")[1]);
        }
        return new StatisticsDto(Long.valueOf(values.get(0)),
                Long.valueOf(values.get(1)),
                values.get(2),
                Double.valueOf(values.get(3)),
                values.get(4),
                Long.valueOf(values.get(5)),
                Double.valueOf(values.get(6)),
                values.get(7),
                BigDecimal.valueOf(Double.valueOf(values.get(8))),
                Double.valueOf(values.get(9)),
                Integer.valueOf(values.get(10)));
    }

}
