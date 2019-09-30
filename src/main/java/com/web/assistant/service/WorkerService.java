package com.web.assistant.service;

import com.web.assistant.dbo.User;
import com.web.assistant.dbo.Worker;
import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import com.web.assistant.repository.AbstractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerService extends AbstractService<WorkerResponseDTO, WorkerRequestDTO, Worker> {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public WorkerService(final AbstractRepository<Worker> repository, final ModelMapper modelMapper, final UserService userService) {
        super(repository);
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public WorkerResponseDTO create(final WorkerRequestDTO workerRequestDTO) {
        final Worker worker = repository.save(modelMapper.map(workerRequestDTO, Worker.class));
        return modelMapper.map(worker, WorkerResponseDTO.class);
    }

    @Override
    public void delete(final Long id) {

    }

    @Override
    public WorkerResponseDTO findOne(final Long id) {
        final Optional<Worker> workerOptional = repository.findById(id);
        if (workerOptional.isPresent()) {
            return modelMapper.map(workerOptional.get(), WorkerResponseDTO.class);
        }
        throw new RuntimeException("Worker not found, wrong id");
    }

    @Override
    public List<WorkerResponseDTO> getList() {
        return repository.findAll().stream().map(worker -> modelMapper.map(worker, WorkerResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void update(final Long id, final WorkerResponseDTO workerResponseDTO) {

    }

    public WorkerResponseDTO create(final WorkerRequestDTO worker, final HttpServletRequest req) {
        final User user = userService.whoami(req);
        worker.setUser(user);
        return create(worker);
    }
}
