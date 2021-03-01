package com.big.friends.app;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    
    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private static final String USER_EXIST_MSG = "User with email %s already exist";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
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

        //todo: send confirmation token

        userRepository.save(user);

        return "User was saved";
    }
}
