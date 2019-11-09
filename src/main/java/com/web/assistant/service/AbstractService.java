package com.web.assistant.service;

import com.web.assistant.converter.DtoConverter;
import com.web.assistant.dbo.AbstractEntity;
import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.dto.AbstractResponseDTO;
import com.web.assistant.repository.AbstractRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public abstract class AbstractService<ResponseDto extends AbstractResponseDTO, RequestDto extends AbstractRequestDTO,
        EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;
    protected final ModelMapper modelMapper;
    protected final DtoConverter<ResponseDto, RequestDto, EntityClass> dtoConverter;
}
