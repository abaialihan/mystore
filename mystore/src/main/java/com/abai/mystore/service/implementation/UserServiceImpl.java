package com.abai.mystore.service.implementation;

import com.abai.mystore.entity.Role;
import com.abai.mystore.entity.Status;
import com.abai.mystore.entity.User;
import com.abai.mystore.reposytory.RoleRepo;
import com.abai.mystore.reposytory.UserRepo;
import com.abai.mystore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RoleRepo roleRepo;
    //для записи пароля с шифрованием
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepo.save(user);

        log.info("In register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepo.findAll();
        log.info("IN getAll - {} user found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String userName) {
        User result = userRepo.findByUsername(userName);
        log.info("IN findByUsername - user: {} found by username: {}", result);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepo.findById(id).orElse(null);

        if (result == null){
            log.info("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}
