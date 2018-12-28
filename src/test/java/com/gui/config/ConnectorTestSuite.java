package com.gui.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConnectorTestSuite {

    @Test
    public void testCheckConnectionStatusFailed() {
        //given
        Connector connector = new Connector();
        String url = "https://www.google.plfefe/";
        //when
        boolean status = connector.checkConnectionStatus(url);
        //then
        Assert.assertFalse(status);
    }

    @Test
    public void testCheckConnectionStatusToLocalHost() {
        //given
        Connector connector = new Connector();
        String url = ServiceConfig.SERVER_URL;
        //when
        boolean status = connector.checkConnectionStatus(url);
        //then
        Assert.assertTrue(status);
    }

}
