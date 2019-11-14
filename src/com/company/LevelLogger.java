package com.company;

public class LevelLogger extends AbstractLogger {
    public LevelLogger(int level) {
        this.level = level;
    }

    @Override
    void write(String message, int level) {
        if (this.level == level) {
            System.out.println(message);
        }
    }
}
