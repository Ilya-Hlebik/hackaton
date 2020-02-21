package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.Skill;
import com.web.hackaton.dbo.Worker;
import com.web.hackaton.dto.request.SkillRequestDto;
import com.web.hackaton.dto.response.SkillResponseDto;
import com.web.hackaton.repository.AbstractRepository;
import com.web.hackaton.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService extends AbstractService<SkillResponseDto, SkillRequestDto, Skill> {

    public SkillService(final AbstractRepository<Skill> repository, final ModelMapper modelMapper,
                        final DtoConverter<SkillResponseDto, SkillRequestDto, Skill> dtoConverter) {
        super(repository, modelMapper, dtoConverter);
    }

    @Transactional
    public void updateSkills(final Worker oldWorker, final Worker newWorker) {
        ((SkillRepository) repository).deleteAllByWorkerAndSkillNameNotIn(oldWorker, newWorker.getSkills()
                .stream().map(Skill::getSkillName).collect(Collectors.toList()));
        final List<Skill> allByWorker = ((SkillRepository) repository).findAllByWorker(oldWorker);
        final List<Skill> skills = newWorker.getSkills().stream()
                .filter(skill -> !allByWorker.stream().map(Skill::getSkillName).collect(Collectors.toList()).contains(skill.getSkillName()))
                .peek(skill -> skill.setWorker(oldWorker))
                .collect(Collectors.toList());
        oldWorker.setSkills(skills);
    }

    public List<SkillResponseDto> getList() {
        return dtoConverter.convertToDto(repository.findAll());
    }
}
