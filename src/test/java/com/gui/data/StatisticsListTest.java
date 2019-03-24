package com.gui.data;

import com.gui.domain.StatisticsRecord;
import com.gui.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class StatisticsListTest {

    @Before
    public void setInstances() {
        User.setUserInstance("362", "test", "test", "some@email.com");
    }

    @After
    public void removeInstance() {
        User.logOutOfUser();
    }


    @Test
    public void testReloadStatistics() {
        //given
        List<StatisticsRecord> recordList;
        //when
        StatisticsList.reloadList();
        recordList = StatisticsList.getRecordList();
        //then
        System.out.println(recordList);
    }


}
