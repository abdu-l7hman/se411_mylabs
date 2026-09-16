package edu.spu.se411.lab06_logging.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsufficientFundsException extends Exception {

    // Step 5: Logger for this exception class
    private static final Logger logger = LoggerFactory.getLogger(InsufficientFundsException.class);

    public InsufficientFundsException(String message) {
        super(message);
        // Step 5: InsufficientFundsException object created → warn
        logger.warn("InsufficientFundsException created: {}", message);
    }
}
