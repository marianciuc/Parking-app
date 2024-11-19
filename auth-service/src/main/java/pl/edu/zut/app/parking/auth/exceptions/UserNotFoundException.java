package pl.edu.zut.app.parking.auth.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
