package pl.edu.zut.app.parking.auth.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.entities.User;
import pl.edu.zut.app.parking.auth.enums.Possibilities;
import pl.edu.zut.app.parking.auth.services.UserPossibilitiesService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPossibilitiesServiceImpl implements UserPossibilitiesService {


    @Override
    public void assignUserPossibility(User user, Possibilities possibility) {
        log.info("Assigning possibility {} to user {}", possibility, user);
        byte[] possibilities = user.getUserPossibilities();
        possibilities[possibility.getId()] = 1;
        user.setUserPossibilities(possibilities);
    }

    @Override
    public void assignUserPossibilities(User user, int[] possibilities) {
        for (int possibility : possibilities) {
            assignUserPossibility(user, Possibilities.values()[possibility]);
        }
    }

    @Override
    public void init(User user) {
        byte [] possibilities = new byte[128];

        log.info("Initializing user {}", user);
        for (int i = 0; i < 128; i++) {
            possibilities[i] = 0;
        }
        user.setUserPossibilities(possibilities);
    }
}
