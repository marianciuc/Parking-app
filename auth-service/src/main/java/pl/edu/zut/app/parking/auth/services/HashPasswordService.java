package pl.edu.zut.app.parking.auth.services;

public interface HashPasswordService {
    String hashPassword(String password);
    boolean checkPassword(String password, String hashedPassword);
}
