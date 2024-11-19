package pl.edu.zut.app.parking.auth.services;

import pl.edu.zut.app.parking.auth.entities.User;

public interface UserRepositoryService {
    User findByUsername(String username);
    User save(User user);
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
}
