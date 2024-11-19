package pl.edu.zut.app.parking.auth.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.dto.common.TokenPair;
import pl.edu.zut.app.parking.auth.dto.req.UserRegistrationRequest;
import pl.edu.zut.app.parking.auth.services.UserRegistrationService;

@Service
public class AdminRegistrationServiceImpl implements UserRegistrationService {

    @Override
    public TokenPair registerUser(UserRegistrationRequest request) {
        // TODO: implement registration
        // TODO: send email with temp credentials
        // TODO: save user to database
        // TODO: log registration
        // TODO: set user role
        return null;
    }
}
