

package com.gui.service;

import com.gui.controller.StatisticsController;
import com.gui.domain.StatisticsRecord;
import com.gui.domain.User;
import com.gui.dto.StatisticsDto;
import com.gui.mapper.StatisticsMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTestSuite {

    @Before
    public void setInstances() {
        User.setUserInstance("5", "test", "test", "some@email.com");
    }

    @After
    public void removeInstance() {
        User.logOutOfUser();
    }

    @InjectMocks
    StatisticsService statisticsService;

    @Mock
    StatisticsController statisticsController;

//    @Mock
//    StatisticsMapper statisticsMapper;

    @Test
    public void testLoadStatistics() {
        //given
        StatisticsDto statisticsDto1 = new StatisticsDto(1, 5, "Test1", 2.43, "2019-01-12", 100L, 2.22, "2019-01-15", BigDecimal.valueOf(-21.00), -8.64, 4);
        StatisticsDto statisticsDto2 = new StatisticsDto(2, 5, "Test2", 4.00, "2019-01-12", 200L, 5.00, "2019-01-15", BigDecimal.valueOf(200.00), 25.00, 4);
        List<StatisticsDto> dtoList = new ArrayList<>();
        dtoList.add(statisticsDto1);
        dtoList.add(statisticsDto2);

        when(statisticsController.getStatistics("5")).thenReturn(dtoList);
        //when
        List<StatisticsRecord> list = statisticsService.loadStatistics();
        //then
        Assert.assertTrue(list.size()>0);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals("2,43 zł", list.get(0).getBuyingPrice());
        Assert.assertEquals("200", list.get(1).getQuantity());
        Assert.assertEquals("2,22 zł", list.get(0).getSellingPrice());
        Assert.assertEquals("5,00 zł", list.get(1).getSellingPrice());
    }



}