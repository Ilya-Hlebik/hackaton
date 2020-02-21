package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.Technology;
import com.web.hackaton.dto.request.TechnologyRequestDto;
import com.web.hackaton.dto.response.TechnologyResponseDto;
import com.web.hackaton.repository.AbstractRepository;
import com.web.hackaton.repository.TechnologyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public TechnologyResponseDto create(final TechnologyRequestDto technologyRequestDto) {
        final Technology technology = dtoConverter.convertToDbo(technologyRequestDto);
        return dtoConverter.convertToDto(repository.save(technology));
    }

    @Transactional
    public void delete(final long id) {
        repository.deleteById(id);
    }
}
