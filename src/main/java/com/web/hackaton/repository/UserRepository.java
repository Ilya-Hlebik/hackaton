package com.web.hackaton.repository;

import com.web.hackaton.dbo.User;

import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepository extends AbstractRepository<User> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    Optional<User> findByEmail(String email);
}
