package org.example.spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog {
    private static final Logger logger= LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        logger.info("Test log4j2");
        logger.debug("Test log4j2");
    }
}
