package pl.edu.zut.app.parking.auth.entities;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserPrincipal extends UserDetails {
    UUID getId();
}
