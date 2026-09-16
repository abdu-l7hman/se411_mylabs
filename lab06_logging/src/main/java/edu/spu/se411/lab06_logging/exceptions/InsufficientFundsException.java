package edu.spu.se411.lab06_logging.exceptions;

/**
 * Thrown when a withdrawal amount exceeds the available wallet balance.
 *
 * AI review note: Logging is NOT done here in the constructor — the exception
 * itself has no context about who is throwing it or why. The caller (WalletAccount)
 * logs at WARN level at the throw site, which gives richer context.
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
        // AI recommendation #1: Do NOT log in the exception constructor.
        // Logging belongs at the throw site (WalletAccount.withdraw) where
        // context (account state, amount) is available.
    }
}
