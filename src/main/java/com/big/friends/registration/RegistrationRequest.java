package com.big.friends.registration;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationRequest {

    private final String name;
    private final String surname;
    private final Date birthday;
    private final String email;
    private final String password;
}
