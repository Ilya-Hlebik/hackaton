package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.AbstractEntity;
import com.web.hackaton.dto.AbstractRequestDTO;
import com.web.hackaton.dto.AbstractResponseDTO;
import com.web.hackaton.repository.AbstractRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public abstract class AbstractService<ResponseDto extends AbstractResponseDTO, RequestDto extends AbstractRequestDTO,
        EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;
    protected final ModelMapper modelMapper;
    protected final DtoConverter<ResponseDto, RequestDto, EntityClass> dtoConverter;
}
