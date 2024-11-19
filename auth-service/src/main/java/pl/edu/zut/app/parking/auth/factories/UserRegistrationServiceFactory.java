package pl.edu.zut.app.parking.auth.factories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.zut.app.parking.auth.enums.UserType;
import pl.edu.zut.app.parking.auth.services.UserRegistrationService;
import pl.edu.zut.app.parking.auth.services.impl.AdminRegistrationServiceImpl;
import pl.edu.zut.app.parking.auth.services.impl.CustomerRegistrationServiceImpl;
import pl.edu.zut.app.parking.auth.services.impl.ParkingRegistrationServiceImpl;

/**
 * Factory class for creating instances of UserRegistrationService based on UserType.
 */
@Component
@AllArgsConstructor
public class UserRegistrationServiceFactory {

    private CustomerRegistrationServiceImpl customerRegistrationService;
    private AdminRegistrationServiceImpl adminRegistrationService;
    private ParkingRegistrationServiceImpl parkingRegistrationService;

    /**
     * Returns the appropriate UserRegistrationService implementation based on the provided UserType.
     *
     * @param userType the type of user for which the registration service is required
     * @return the corresponding UserRegistrationService implementation
     * @throws IllegalArgumentException if the provided UserType is unknown
     * @see UserRegistrationService
     * @see UserType
     */
    public UserRegistrationService getRegistrationService(UserType userType) {
        return switch (userType) {
            case CUSTOMER -> customerRegistrationService;
            case ADMIN -> adminRegistrationService;
            case PARKING_OWNER -> parkingRegistrationService;
            default -> throw new IllegalArgumentException("Unknown user type: " + userType);
        };
    }

}
