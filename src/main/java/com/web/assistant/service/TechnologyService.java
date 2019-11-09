package com.web.assistant.service;

import com.web.assistant.converter.DtoConverter;
import com.web.assistant.dbo.Technology;
import com.web.assistant.dto.request.TechnologyRequestDto;
import com.web.assistant.dto.response.TechnologyResponseDto;
import com.web.assistant.repository.AbstractRepository;
import com.web.assistant.repository.TechnologyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyService extends AbstractService<TechnologyResponseDto, TechnologyRequestDto, Technology> {

    public TechnologyService(final AbstractRepository<Technology> repository, final ModelMapper modelMapper,
                             final DtoConverter<TechnologyResponseDto, TechnologyRequestDto, Technology> dtoConverter, final TechnologyRepository technologyRepository) {
        super(repository, modelMapper, dtoConverter);
    }

    public List<Technology> findTechnologiesByNames(final List<Technology> technologies) {
        final List<String> technologyNames = technologies.stream().map(Technology::getTechnologyName)
                .collect(Collectors.toList());
        if (technologyNames.isEmpty()) {
            return Collections.emptyList();
        }
        return ((TechnologyRepository) repository).findAllByTechnologyNameIn(technologyNames);
    }

    public List<TechnologyResponseDto> getList() {
        return dtoConverter.convertToDto(repository.findAll());
    }
}
