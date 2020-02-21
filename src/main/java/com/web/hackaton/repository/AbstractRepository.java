package com.web.hackaton.repository;

import com.web.hackaton.dbo.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<EntityClass extends AbstractEntity> extends JpaRepository<EntityClass, Long> {
}
