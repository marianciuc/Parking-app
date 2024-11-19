package pl.edu.zut.app.parking.auth.services;

import pl.edu.zut.app.parking.auth.entities.User;
import pl.edu.zut.app.parking.auth.enums.Possibilities;

public interface UserPossibilitiesService {
    void assignUserPossibility(User user, Possibilities possibility);
    void assignUserPossibilities(User user, int[] possibilities);
    void init(User user);
}
