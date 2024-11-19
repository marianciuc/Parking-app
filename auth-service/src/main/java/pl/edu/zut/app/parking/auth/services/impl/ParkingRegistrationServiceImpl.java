package pl.edu.zut.app.parking.auth.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.dto.common.TokenPair;
import pl.edu.zut.app.parking.auth.dto.req.UserRegistrationRequest;
import pl.edu.zut.app.parking.auth.entities.User;
import pl.edu.zut.app.parking.auth.enums.Possibilities;
import pl.edu.zut.app.parking.auth.enums.UserType;
import pl.edu.zut.app.parking.auth.services.HashPasswordService;
import pl.edu.zut.app.parking.auth.services.UserPossibilitiesService;
import pl.edu.zut.app.parking.auth.services.UserRegistrationService;
import pl.edu.zut.app.parking.auth.services.UserRepositoryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepositoryService userRepositoryService;
    private final PasswordEncoder passwordEncoder;
    private final UserPossibilitiesService userPossibilitiesService;

    private static final int[] parkingOwnerPossibilities = {
            Possibilities.LOGIN_IN_PARKING_PANEL.getId(),
    };

    @Override
    public TokenPair registerUser(UserRegistrationRequest request) {
        // TODO: implement registration
        log.info("Registering user {}", request);

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .username(request.username())
                .userType(UserType.PARKING_OWNER)
                .build();

        userPossibilitiesService.init(user);
        userPossibilitiesService.assignUserPossibilities(user, parkingOwnerPossibilities);
        userRepositoryService.save(user);
        return null;
    }
}
