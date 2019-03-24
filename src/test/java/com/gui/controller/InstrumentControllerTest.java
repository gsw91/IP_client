package com.gui.controller;

import com.gui.domain.User;
import com.gui.dto.InstrumentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InstrumentControllerTest {

    @Test
    public void testAddInstrument() {
        //given
        User user = User.getUserInstance();
        InstrumentController controller = new InstrumentController();
        InstrumentDto instrumentDto = new InstrumentDto(111L, 1000L, "PKNORLEN", 100.00, "2018-12-17");
        controller.addShare(instrumentDto);
        //when

        //then

    }

}
