package com.web.hackaton.converter;

import com.web.hackaton.dbo.Skill;
import com.web.hackaton.dto.request.SkillRequestDto;
import com.web.hackaton.dto.response.SkillResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SkillConverter implements DtoConverter<SkillResponseDto, SkillRequestDto, Skill>{

    @Override
    public SkillResponseDto convertToDto(final Skill dbo) {
        final SkillResponseDto skillResponseDto = new SkillResponseDto();
        BeanUtils.copyProperties(dbo, skillResponseDto);
        return skillResponseDto;
    }

    @Override
    public Skill convertToDbo(final SkillRequestDto dto) {
        final Skill skill = new Skill();
        BeanUtils.copyProperties(dto, skill);
        return skill;
    }
}
