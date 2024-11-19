package pl.edu.zut.app.parking.auth.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.services.HashPasswordService;

@Service
@Slf4j
public class HashPasswordServiceImpl implements HashPasswordService {

    @Override
    public String hashPassword(String password) {
        return "";
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        return false;
    }
}
