package com.web.assistant.service;

import com.web.assistant.dbo.AbstractEntity;
import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.dto.AbstractResponseDTO;
import com.web.assistant.repository.AbstractRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractService<ResponseDto extends AbstractResponseDTO,RequestDto extends AbstractRequestDTO, EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;
    protected final ModelMapper modelMapper;

    public abstract ResponseDto create(final RequestDto dtoClass, final HttpServletRequest req);

    public abstract ResponseDto update(final RequestDto responseDto, final HttpServletRequest req);

    public abstract void delete(final HttpServletRequest req);

    public abstract ResponseDto findOne(final long id);

    public abstract List<ResponseDto> getList();
}
