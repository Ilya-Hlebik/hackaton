package com.web.assistant.converter;

import com.web.assistant.dbo.Worker;
import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WorkerConverter implements DtoConverter<WorkerResponseDTO, WorkerRequestDTO, Worker> {

    @Override
    public WorkerResponseDTO convertToDto(final Worker dbo) {
        final WorkerResponseDTO workerResponseDTO = new WorkerResponseDTO();
        BeanUtils.copyProperties(dbo, workerResponseDTO);
        workerResponseDTO.setPositions(dbo.getPositions().stream().map(position -> position.getPositionName().getCaption())
                .collect(Collectors.toList()));
        workerResponseDTO.setStatus(dbo.getStatus().getCaption());
        return workerResponseDTO;
    }

    @Override
    public Worker convertToDbo(final WorkerRequestDTO dto) {
        final Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        worker.setPositions(dto.getPosition());
        return worker;
    }
}
