package com.web.hackaton.converter;

import com.web.hackaton.dbo.Technology;
import com.web.hackaton.dto.request.TechnologyRequestDto;
import com.web.hackaton.dto.response.TechnologyResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TechnologyConverter implements DtoConverter<TechnologyResponseDto, TechnologyRequestDto, Technology> {

    private final SkillConverter skillConverter;

    @Override
    public TechnologyResponseDto convertToDto(final Technology dbo) {
        final TechnologyResponseDto technologyResponseDto = new TechnologyResponseDto();
        BeanUtils.copyProperties(dbo, technologyResponseDto);
        return technologyResponseDto;
    }

    @Override
    public Technology convertToDbo(final TechnologyRequestDto dto) {
        final Technology technology = new Technology();
        BeanUtils.copyProperties(dto, technology);
        return technology;
    }
}
