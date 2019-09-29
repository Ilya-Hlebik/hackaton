package com.web.assistant.service;

import com.web.assistant.dbo.AbstractEntity;
import com.web.assistant.dto.AbstractDto;
import com.web.assistant.repository.AbstractRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractService<DtoClass extends AbstractDto, EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;

    public abstract DtoClass create(final DtoClass dtoClass);

    public abstract void update(final Long id, final DtoClass dtoClass);

    public abstract void delete(final Long id);

    public abstract DtoClass findOne(final Long id);

    public abstract List<DtoClass> getList();
}
