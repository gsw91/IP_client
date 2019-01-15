package com.gui.update;

import com.gui.controllerFX.UserSceneController;
import net.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.concurrent.TimeoutException;

@RunWith(MockitoJUnitRunner.class)
public class ThreadUpdateTestSuite {

    @InjectMocks
    private UserSceneController userSceneController;

    private ThreadUpdate threadUpdate;

    @Test
    public void testThreadUpdate() throws TimeoutException {
        final Waiter waiter = new Waiter();
        threadUpdate = new ThreadUpdate(userSceneController);

        new Thread(() -> {
            waiter.assertTrue(true);
            waiter.resume();
        }).start();

        // Wait for resume() to be called
        waiter.await(1000);
    }

    @Test
    public void testRefreshUserInstrumentPrice() {

    }

    @Test
    public void testCalculateShareRatios() {

    }



}
