package com.darjeedes.timetracker;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;

/**
 * Represents a handler for all unhandled and unchecked exceptions that may be thrown.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger("timetracker.log");

    /**
     * Shows an alert to the user and logs the exception.
     *
     * @param t the thread that threw the exception
     * @param e the exception that was thrown
     */
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        // TODO: Log the Exception and the whole stack trace. Implement logging to file. Careful: expected exception
        //  may be wrapped in another exception (InvocationTargetException).
        LOGGER.log(Level.SEVERE, e.getMessage());
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Sorry, but something went wrong. (" + e.getMessage() + ")").show();
    }

}
