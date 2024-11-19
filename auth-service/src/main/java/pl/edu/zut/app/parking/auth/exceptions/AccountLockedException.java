package pl.edu.zut.app.parking.auth.exceptions;

public class AccountLockedException extends RuntimeException{

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountLockedException(String message) {
        super(message);
    }
}
