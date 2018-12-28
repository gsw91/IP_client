package com.gui.update;

import com.gui.controllerFX.UserSceneController;
import org.apache.log4j.Logger;

public class ThreadConfig {

    private UserSceneController userSceneController;
    private Thread thread;

    public ThreadConfig(UserSceneController userSceneController) {
        this.userSceneController = userSceneController;
    }

    private Logger logger = Logger.getLogger(ThreadConfig.class);

    public void startThreadUpdate() {
        Runnable runners = new ThreadUpdate(userSceneController);
        thread = new Thread(runners);
        thread.start();
        logger.info("Update thread has started");
    }

    public void interruptThreadUpdate() {
            thread.interrupt();
            logger.info("Update thread has stopped");

    }

}
