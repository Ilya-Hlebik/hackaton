package com.web.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<EntityClass> extends JpaRepository<EntityClass, Long> {
}
