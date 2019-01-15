package com.gui.controller;

import com.gui.dto.StatisticsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class StatisticsControllerTestSuite {

    @Test
    public void testGetStatistics() {
        //given
        StatisticsController statisticsController = new StatisticsController();
        //when
        List<StatisticsDto> dtoList = statisticsController.getStatistics("362");
        //then
        Assert.assertTrue(dtoList.size()>0);

    }

}
