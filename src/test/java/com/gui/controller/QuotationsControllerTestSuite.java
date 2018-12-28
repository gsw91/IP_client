package com.gui.controller;

import com.gui.config.ServiceConfig;
import com.gui.dto.ShareDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(JUnit4.class)
public class QuotationsControllerTestSuite {

    @Test
    public void testSetResponseToMap() {
        //before
        boolean isAvailable;
        try {
            URL urlPath = new URL(ServiceConfig.SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
            connection.getResponseCode();
            isAvailable = true;
        } catch (Exception e) {
            isAvailable = false;
        }
        if(isAvailable) {
            //given
            QuotationsController quotationsController = new QuotationsController();
            String response = quotationsController.getCurrentQuotationsResponse();
            //when & then
            System.out.println(response);
            Assert.assertTrue(response.length() > 1000);
        }
    }

    @Test
    public void testSetQuotationsMap() {
        //given
        QuotationsController quotationsController = new QuotationsController();
        String response = "{\"11BIT\":{\"index\":\"11BIT\",\"price\":235.0,\"serverActualization\":\"2018-11-20T17:01:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}," +
                "\"4FUNMEDIA\":{\"index\":\"4FUNMEDIA\",\"price\":13.0,\"serverActualization\":\"2018-11-20T17:00:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}," +
                "\"ABCDATA\":{\"index\":\"ABCDATA\",\"price\":0.78,\"serverActualization\":\"2018-11-20T16:02:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}," +
                "\"ABPL\":{\"index\":\"ABPL\",\"price\":17.2,\"serverActualization\":\"2018-11-20T17:00:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}," +
                "\"ACAUTOGAZ\":{\"index\":\"ACAUTOGAZ\",\"price\":40.4,\"serverActualization\":\"2018-11-20T11:29:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}," +
                "\"ADIUVO\":{\"index\":\"ADIUVO\",\"price\":9.5,\"serverActualization\":\"2018-11-20T13:53:00\",\"applicationActualization\":\"2018-11-20T22:40:02.152\"}}";
        //when
        Map<String, ShareDto> currentQuotations = quotationsController.setQuotationsMap(response);
        //then
        Assert.assertEquals(6, currentQuotations.size());
        Assert.assertTrue(currentQuotations.keySet().contains("ABPL"));
        Assert.assertEquals(235.0, currentQuotations.get("11BIT").getCurrentPrice(), 0.01);
        Assert.assertEquals(LocalDateTime.parse("2018-11-20T11:29:00"), currentQuotations.get("ACAUTOGAZ").getServerActualization());
        Assert.assertEquals(LocalDateTime.parse("2018-11-20T22:40:02.152"), currentQuotations.get("ADIUVO").getApplicationActualization());
        Assert.assertEquals("4FUNMEDIA", currentQuotations.get("4FUNMEDIA").getIndex());
    }

    @Test
    public void testSetQuotationsMapWhenEmptyResponse() {
        //given
        QuotationsController quotationsController = new QuotationsController();
        String response = "";
        //when
        Map<String, ShareDto> currentQuotations = quotationsController.setQuotationsMap(response);
        //then
        Assert.assertEquals(0, currentQuotations.size());
    }

    @Test
    public void testParseToShareDto() {
        //given
        QuotationsController quotationsController = new QuotationsController();
        String data = "{index=11BIT, price=235.0, serverActualization=2018-11-20T17:01:00, applicationActualization=2018-11-20T21:55:00.826}";
        //when
        ShareDto shareDto = quotationsController.parseToShareDto(data);
        //then
        Assert.assertEquals("11BIT", shareDto.getIndex());
        Assert.assertEquals(235.0, shareDto.getCurrentPrice(), 0.01);
        Assert.assertEquals(LocalDateTime.parse("2018-11-20T17:01:00"), shareDto.getServerActualization());
        Assert.assertEquals(LocalDateTime.parse("2018-11-20T21:55:00.826"), shareDto.getApplicationActualization());
    }

}
