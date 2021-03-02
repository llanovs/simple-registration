package com.big.friends.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;
    private static final String TOKEN_NOT_FOUND = "Token not found";

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        repository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return repository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
