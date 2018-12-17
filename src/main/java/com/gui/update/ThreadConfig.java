package com.gui.update;

import com.gui.controllerFX.UserController;
import org.apache.log4j.Logger;

public class ThreadConfig {

    private UserController userController;
    private Thread thread;

    public ThreadConfig(UserController userController) {
        this.userController = userController;
    }

    private Logger logger = Logger.getLogger(ThreadConfig.class);

    public void startThreadUpdate() {
        Runnable runners = new ThreadUpdate(userController);
        thread = new Thread(runners);
        thread.start();
        logger.info("Update thread has started");
    }

    public void interruptThreadUpdate() {
            thread.interrupt();
            logger.info("Update thread has stopped");

    }

}
