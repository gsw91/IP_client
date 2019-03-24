package com.gui.controller;

import com.gui.domain.User;
import com.gui.dto.StatisticsDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {

    @Before
    public void setInstances() {
        User.setUserInstance("5", "test", "test", "some@email.com");
    }

    @After
    public void removeInstance() {
        User.logOutOfUser();
    }

    @Test
    public void testGetStatistics() {
        //given
        StatisticsController statisticsController = new StatisticsController();
        //when
        List<StatisticsDto> dtoList = statisticsController.getStatistics(User.getUserInstance().getId());
        //then
        Assert.assertEquals(0, dtoList.size());
    }

}