package com.gui.service;

import com.gui.Main;
import com.gui.controller.InstrumentController;
import com.gui.dto.InstrumentDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

public class UserOperationTestSuite extends ApplicationTest {

    @Ignore
    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

 //   @InjectMocks
  //  private InstrumentController instrumentController;

 //   @Mock
    private UserOperation userOperation = new UserOperation();

    @Ignore
    @Test
    public void testBuyShare() {
        //given
//        Long userId = 111;
        String instrument = "PKNORLEN";
        String quantity = "1000";
        String price = "10.25";
//        when(instrumentController.addShare())
//        String date = "2018-12-27";
//        InstrumentDto instrumentDto = new InstrumentDto(userId, quantity, instrument, price, date);
        //when
        boolean isAdded = userOperation.buyShare(instrument, quantity, price);
        //then
        Assert.assertTrue(isAdded);
    }


}
