package com.web.assistant.service;

import com.web.assistant.dbo.Worker;
import com.web.assistant.dto.WorkerDTO;
import com.web.assistant.repository.AbstractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerService extends AbstractService<WorkerDTO, Worker> {
    private final ModelMapper modelMapper;

    public WorkerService(final AbstractRepository<Worker> repository, final ModelMapper modelMapper) {
        super(repository);
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkerDTO create(final WorkerDTO workerDTO) {
        final Worker save = repository.save(modelMapper.map(workerDTO, Worker.class));
        return modelMapper.map(save, WorkerDTO.class);
    }

    @Override
    public void update(final Long id, final WorkerDTO workerDTO) {

    }

    @Override
    public void delete(final Long id) {

    }

    @Override
    public WorkerDTO findOne(final Long id) {
        final Optional<Worker> workerOptional = repository.findById(id);
        if (workerOptional.isPresent()) {
            return modelMapper.map(workerOptional.get(), WorkerDTO.class);
        }
        throw new RuntimeException("Worker not found, wrong id");
    }

    @Override
    public List<WorkerDTO> getList() {
        return repository.findAll().stream().map(worker -> modelMapper.map(worker, WorkerDTO.class)).collect(Collectors.toList());
    }
}
