package com.big.friends.registration;

import com.big.friends.app.AppUserRole;
import com.big.friends.app.UserEntity;
import com.big.friends.app.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String EMAIL_NOT_VALID = "Email %s not valid";
    private EmailValidator emailValidator;
    private UserService userService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw  new IllegalArgumentException(String.format(EMAIL_NOT_VALID, request.getEmail()));
        }

        return userService.addUser(new UserEntity(
                                    request.getName(),
                                    request.getSurname(),
                                    request.getBirthday(),
                                    request.getEmail(),
                                    request.getPassword(),
                                    AppUserRole.USER));
    }
}
