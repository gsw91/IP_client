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

    @Mock
    StatisticsMapper statisticsMapper;

    @Test
    public void testLoadStatistics() {
        //given
        StatisticsDto statisticsDto1 = new StatisticsDto(1, 5, "Test1", 2.43, "2019-01-12", 100L, 2.22, "2019-01-15", BigDecimal.valueOf(-21.00), -8.64, 4);
        StatisticsDto statisticsDto2 = new StatisticsDto(2, 5, "Test2", 4.00, "2019-01-12", 200L, 5.00, "2019-01-15", BigDecimal.valueOf(200.00), 25.00, 4);
        List<StatisticsDto> dtoList = new ArrayList<>();
        dtoList.add(statisticsDto1);
        dtoList.add(statisticsDto2);

        StatisticsRecord statisticsRecord1 = new StatisticsRecord("Test1", "2.43", "2019-01-12", "100L", "2.22", "2019-01-15", "-21.00", "-8.64", "4");
        StatisticsRecord statisticsRecord2 = new StatisticsRecord("Test2", "4.00", "2019-01-12", "200L", "5.00", "2019-01-15", "200.00", "25.00", "4");
        List<StatisticsRecord> recordList = new ArrayList<>();
        recordList.add(statisticsRecord1);
        recordList.add(statisticsRecord2);

//        when(statisticsService.loadStatistics()).thenCallRealMethod();
        when(statisticsController.getStatistics("5")).thenReturn(dtoList);
        when(statisticsMapper.mapToRecordList(dtoList)).thenReturn(recordList);
        //when
        List<StatisticsRecord> list = statisticsService.loadStatistics();
        //then
        Assert.assertTrue(list.size()>0);
        Assert.assertEquals(list.size(), 2);
        System.out.println(dtoList);
        System.out.println(list);
    }



}
