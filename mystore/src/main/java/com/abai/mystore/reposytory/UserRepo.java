package com.abai.mystore.reposytory;

import com.abai.mystore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
