package com.big.friends.app;

import com.big.friends.registration.token.ConfirmationToken;
import com.big.friends.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    
    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private static final String USER_EXIST_MSG = "User with email %s already exist";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    public String addUser(UserEntity user){
        boolean isUserExist = userRepository.findByEmail(user.getEmail()).isPresent();

        if (isUserExist){
            throw new IllegalStateException(String.format(USER_EXIST_MSG, user.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return sendConfirmationToken(user);
    }

    public String sendConfirmationToken(UserEntity userEntity){

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEntity
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableUser(String email) {
       return userRepository.enableAppUser(email);
    }
}
