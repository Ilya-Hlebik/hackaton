package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.Position;
import com.web.hackaton.dto.request.PositionRequestDto;
import com.web.hackaton.dto.response.PositionResponseDto;
import com.web.hackaton.repository.AbstractRepository;
import com.web.hackaton.repository.PositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService extends AbstractService<PositionResponseDto, PositionRequestDto, Position> {
    public PositionService(final AbstractRepository<Position> repository, final ModelMapper modelMapper,
                           final DtoConverter<PositionResponseDto, PositionRequestDto, Position> dtoConverter) {
        super(repository, modelMapper, dtoConverter);
    }


    public List<Position> findPositionsByNames(final List<Position> positions) {
        final List<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toList());
        if (positionNames.isEmpty()) {
            return Collections.emptyList();
        }
        return ((PositionRepository) repository).findAllByPositionNameIn(positionNames);
    }


    public List<PositionResponseDto> getList() {
        return dtoConverter.convertToDto(repository.findAll());
    }

    public PositionResponseDto create(final PositionRequestDto positionRequestDto) {
        final Position position = dtoConverter.convertToDbo(positionRequestDto);
        return dtoConverter.convertToDto(repository.save(position));
    }

    @Transactional
    public void delete(final long id) {
        repository.deleteById(id);
    }
}
