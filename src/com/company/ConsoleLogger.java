package com.company;

public class ConsoleLogger extends AbstractLogger {
    @Override
    void write(String message, int level) {
        System.out.println(message);
    }
}
