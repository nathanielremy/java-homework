package com.company;

public class ChainPatternDemo {
    public AbstractLogger logger;


    public static void main(String[] args) {
        AbstractLogger log1 = new ConsoleLogger();
        AbstractLogger log2 = new LevelLogger(AbstractLogger.DEBUG);
        AbstractLogger log3 = new LevelLogger(AbstractLogger.ERROR);
        AbstractLogger log4 = new LevelLogger(AbstractLogger.ERROR);

        log1.setNextLogger(log2);
        log2.setNextLogger(log3);
        log3.setNextLogger(log4);

        ChainPatternDemo demo = new ChainPatternDemo();
        demo.logger = log1;

        demo.logger.logMessage("Hello", AbstractLogger.INFO);
        demo.logger.logMessage("World", AbstractLogger.DEBUG);
        demo.logger.logMessage("Harour.Space", AbstractLogger.ERROR);
    }
}
