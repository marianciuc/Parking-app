package pl.edu.zut.app.parking.auth.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.entities.User;
import pl.edu.zut.app.parking.auth.exceptions.UserNotFoundException;
import pl.edu.zut.app.parking.auth.repositories.UserRepository;
import pl.edu.zut.app.parking.auth.services.UserRepositoryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return repository.findByUsernameOrEmail(username, email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
