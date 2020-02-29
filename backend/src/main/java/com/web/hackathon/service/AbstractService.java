package com.web.hackathon.service;

import com.web.hackathon.dbo.AbstractEntity;
import com.web.hackathon.repository.AbstractRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public abstract class AbstractService<EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;
    protected final ModelMapper modelMapper;
}
