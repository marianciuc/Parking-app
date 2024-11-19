package pl.edu.zut.app.parking.auth.services;

import pl.edu.zut.app.parking.auth.dto.common.TokenPair;
import pl.edu.zut.app.parking.auth.dto.req.UserRegistrationRequest;

public interface UserRegistrationService {
    TokenPair registerUser(UserRegistrationRequest request);
}
