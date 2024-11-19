package pl.edu.zut.app.parking.auth.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
