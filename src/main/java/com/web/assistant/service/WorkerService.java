package com.web.assistant.service;

import com.web.assistant.dbo.User;
import com.web.assistant.dbo.Worker;
import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import com.web.assistant.repository.AbstractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    public WorkerResponseDTO findOne(final long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(), WorkerResponseDTO.class);
    }

    @Override
    public List<WorkerResponseDTO> getList() {
        return repository.findAll().stream().map(worker -> modelMapper.map(worker, WorkerResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public WorkerResponseDTO create(final WorkerRequestDTO worker, final HttpServletRequest req) {
        final User user = userService.whoami(req);
        worker.setUser(user);
        final Worker savedWorker = repository.save(modelMapper.map(worker, Worker.class));
        return modelMapper.map(savedWorker, WorkerResponseDTO.class);
    }

    @Override
    public WorkerResponseDTO update(final WorkerRequestDTO responseDto, final HttpServletRequest req) {
        final User user = userService.whoami(req);
        final Worker oldWorker = user.getWorker();
        final Worker newWorker = modelMapper.map(responseDto, Worker.class);
        newWorker.setId(oldWorker.getId());
        newWorker.setUser(user);
        return modelMapper.map(repository.save(newWorker), WorkerResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(final HttpServletRequest req) {
        final User user = userService.whoami(req);
        repository.delete(user.getWorker());
        user.setWorker(null);
    }

    public WorkerResponseDTO findMe(final HttpServletRequest req) {
        final User user = userService.whoami(req);
        return modelMapper.map(Optional.ofNullable(user.getWorker()).orElseThrow(), WorkerResponseDTO.class);
    }

}
