package edu.spu.se411.lab06_logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab06_logging.exceptions.InsufficientFundsException;
import edu.spu.se411.lab06_logging.model.WalletAccount;

public class App {

    // Step 2a: static Logger instance
    static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        // Step 2b / Step 5: log app start at INFO
        logger.info("Application is starting...");

        // ── Scenario 1: over-withdrawal ──────────────────────────────────────
        WalletAccount account = new WalletAccount(1000);

        try {
            account.withdraw(1500);
        } catch (InsufficientFundsException e) {
            // Step 5: log thrown exception at ERROR
            logger.error("Caught exception during withdrawal: {}", e.getMessage(), e);
        }

        // ── Scenario 2: invalid deposit (negative) ───────────────────────────
        try {
            account.deposit(-100);
        } catch (IllegalArgumentException e) {
            logger.error("Caught exception during deposit: {}", e.getMessage(), e);
        }

        // ── Scenario 3: successful operations ───────────────────────────────
        WalletAccount account2 = new WalletAccount(500);
        try {
            account2.deposit(200);
            account2.withdraw(100);
        } catch (InsufficientFundsException e) {
            logger.error("Caught exception: {}", e.getMessage(), e);
        }

        // Step 5: log app end at INFO
        logger.info("Application is ending.");
    }
}
