package com.company;

public abstract class AbstractLogger {
    public static final int INFO = 10;
    public static final int DEBUG = 20;
    public static final int ERROR = 100;

    int level = 0;

    private AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(String message, int level) {
        write(message, level);
        if (nextLogger != null)
            nextLogger.logMessage(message, level);
    }

    abstract void write(String message, int level);
}
