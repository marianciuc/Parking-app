package pl.edu.zut.app.parking.auth.exceptions;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredTokenException(String message) {
        super(message);
    }
}
