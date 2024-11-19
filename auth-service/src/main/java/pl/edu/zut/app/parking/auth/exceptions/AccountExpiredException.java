package pl.edu.zut.app.parking.auth.exceptions;

public class AccountExpiredException extends RuntimeException{

    public AccountExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExpiredException(String message) {
        super(message);
    }
}
