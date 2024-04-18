package com.br.projeto.service.user;

import com.br.projeto.entity.user.User;
import com.br.projeto.exception.business.ObjectNotFoundException;
import com.br.projeto.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User for the given id %s was not found", id)));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User for the given email %s was not found", email)));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public User createUser(User userEntity) {
        var bcryptPassword = new BCryptPasswordEncoder();

        userEntity.setPassword(bcryptPassword.encode(userEntity.getPassword()));

        return saveUser(userEntity);
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= 60;
    }
}
