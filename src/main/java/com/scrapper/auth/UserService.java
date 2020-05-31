package com.scrapper.auth;

import com.scrapper.auth.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserService {
    void save(User user) throws InvalidKeySpecException, NoSuchAlgorithmException;

    User findByUsername(String username);

    Boolean hash();
}
