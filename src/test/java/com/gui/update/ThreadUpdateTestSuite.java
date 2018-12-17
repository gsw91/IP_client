package com.gui.update;

import com.gui.controllerFX.UserController;
import net.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeoutException;

@RunWith(MockitoJUnitRunner.class)
public class ThreadUpdateTestSuite {

    @InjectMocks
    private UserController userController;

    private ThreadUpdate threadUpdate;

    @Test
    public void testThreadUpdate() throws TimeoutException {
        final Waiter waiter = new Waiter();
        threadUpdate = new ThreadUpdate(userController);

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
