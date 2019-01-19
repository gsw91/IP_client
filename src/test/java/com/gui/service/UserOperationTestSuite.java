package com.gui.service;

import com.gui.controller.InstrumentController;
import com.gui.controller.UserController;
import com.gui.data.CalculationMap;
import com.gui.data.QuotationsMap;
import com.gui.domain.InstrumentCalculation;
import com.gui.domain.User;
import com.gui.dto.InstrumentDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserOperationTestSuite extends ApplicationTest {

    @InjectMocks
    private UserOperation userOperation;

    @Mock
    private UserController userController;

    @Mock
    private InstrumentController instrumentController;

    @Before
    public void setTestData() {
        QuotationsMap.setCurrentQuotations();
        User.setUserInstance("111", "test", "test", "test@test.com");
        CalculationMap.getData().put(1L, new InstrumentCalculation(1L, "PKNORLEN", 10L, BigDecimal.valueOf(99.12)));
    }

    @After
    public void clearTestData() {

    }

    @Test
    public void testBuyShare() {
        //given
        long userId = 111L;
        String instrument = "PKNORLEN";
        long quantity = 1000L;
        double price = 10.25;
        String date = String.valueOf(LocalDate.now());
        InstrumentDto instrumentDto = new InstrumentDto(userId, quantity, instrument, price, date);
        when(instrumentController.addShare(instrumentDto)).thenReturn(true);
        //when
        boolean isAdded = userOperation.buyShare("PKNORLEN", "1000", "10.25");
        //then
        Assert.assertTrue(isAdded);
    }

    @Test
    public void testDeleteAccount() {
        //given
        when(userController.deleteAccount("111")).thenReturn("true");
        //when
        boolean isDeleted = userOperation.deleteAccount();
        //then
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void testSellShare() {
        //given
        when(instrumentController.sellShare("PKNORLEN", "10", "101.01")).thenReturn(true);
        //when
        boolean isSold = userOperation.sellShare("PKNORLEN", "10", "101.01");
        //then
        Assert.assertTrue(isSold);
    }

}
