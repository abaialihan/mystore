package com.abai.mystore.reposytory;

import com.abai.mystore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
