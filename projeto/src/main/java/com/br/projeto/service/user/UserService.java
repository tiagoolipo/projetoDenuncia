package com.br.projeto.service.user;

import com.br.projeto.entity.user.User;

import java.util.UUID;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

    User saveUser(User user);
}
