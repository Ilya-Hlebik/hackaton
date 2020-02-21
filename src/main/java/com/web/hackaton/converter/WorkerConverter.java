package com.web.hackaton.converter;

import com.web.hackaton.dbo.Worker;
import com.web.hackaton.dto.request.WorkerRequestDTO;
import com.web.hackaton.dto.response.WorkerResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkerConverter implements DtoConverter<WorkerResponseDTO, WorkerRequestDTO, Worker> {

    private final PositionConverter positionConverter;
    private final TechnologyConverter technologyConverter;
    private final SkillConverter skillConverter;

    @Override
    public WorkerResponseDTO convertToDto(final Worker dbo) {
        final WorkerResponseDTO workerResponseDTO = new WorkerResponseDTO();
        BeanUtils.copyProperties(dbo, workerResponseDTO);
        workerResponseDTO.setPositions(positionConverter.convertToDto(dbo.getPositions()));
        workerResponseDTO.setStatus(dbo.getStatus().getCaption());
        workerResponseDTO.setTechnologies(technologyConverter.convertToDto(dbo.getTechnologies()));
        workerResponseDTO.setSkills(skillConverter.convertToDto(dbo.getSkills()));
        return workerResponseDTO;
    }

    @Override
    public Worker convertToDbo(final WorkerRequestDTO dto) {
        final Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        worker.setPositions(positionConverter.convertToDbo(dto.getPosition()));
        worker.setTechnologies(technologyConverter.convertToDbo(dto.getTechnologies()));
        worker.setSkills(skillConverter.convertToDbo(dto.getSkills()));
        return worker;
    }
}
