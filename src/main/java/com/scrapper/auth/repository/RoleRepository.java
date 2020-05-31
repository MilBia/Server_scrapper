package com.scrapper.auth.repository;

import com.scrapper.auth.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
