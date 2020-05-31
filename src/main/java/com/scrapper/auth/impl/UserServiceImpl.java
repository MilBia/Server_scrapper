package com.scrapper.auth.impl;

import com.scrapper.auth.UserService;
import com.scrapper.auth.model.User;
import com.scrapper.auth.repository.RoleRepository;
import com.scrapper.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collection;

import static com.scrapper.utilities.Hash.HashPassword;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        user.setSalt(Base64.getEncoder().encodeToString(salt));
        user.setPassword(Base64.getEncoder().encodeToString(HashPassword(user.getPassword(), salt)));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public Boolean hash() {
        try {
            Collection<User> users = (Collection<User>) userRepository.findAll();
            for (User u : users) {
                if(u.getSalt().equals("")) {
                    this.save(u);
                }
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
