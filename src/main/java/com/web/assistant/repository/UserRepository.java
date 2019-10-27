package com.web.assistant.repository;

import com.web.assistant.dbo.User;

import javax.transaction.Transactional;


public interface UserRepository extends AbstractRepository<User> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
