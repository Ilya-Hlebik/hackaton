package com.web.assistant.service;

import com.web.assistant.dbo.AbstractEntity;
import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.dto.AbstractResponseDTO;
import com.web.assistant.repository.AbstractRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractService<ResponseDto extends AbstractResponseDTO,RequestDto extends AbstractRequestDTO, EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;

    public abstract ResponseDto create(final RequestDto dtoClass);

    public abstract void update(final Long id, final ResponseDto responseDto);

    public abstract void delete(final Long id);

    public abstract ResponseDto findOne(final Long id);

    public abstract List<ResponseDto> getList();
}
