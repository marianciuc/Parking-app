package pl.edu.zut.app.parking.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.zut.app.parking.auth.dto.common.TokenPair;
import pl.edu.zut.app.parking.auth.dto.req.UserRegistrationRequest;
import pl.edu.zut.app.parking.auth.enums.UserType;
import pl.edu.zut.app.parking.auth.factories.UserRegistrationServiceFactory;
import pl.edu.zut.app.parking.auth.services.UserRegistrationService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRegistrationServiceFactory userRegistrationServiceFactory;

    @PostMapping("/register/{userType}")
    public ResponseEntity<TokenPair> register(@RequestBody UserRegistrationRequest request,
                                              @PathVariable UserType userType) {
        UserRegistrationService userRegistrationService =
                userRegistrationServiceFactory.getRegistrationService(userType);
        userRegistrationService.registerUser(request);
        return null;
    }
}
