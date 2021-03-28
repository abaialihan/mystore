package com.abai.mystore.reposytory;

import com.abai.mystore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleRepo, Long> {
    Role findByName(String name);
}
