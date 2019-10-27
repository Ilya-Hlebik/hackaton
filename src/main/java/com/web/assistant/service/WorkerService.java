package com.web.assistant.service;

import com.web.assistant.converter.DtoConverter;
import com.web.assistant.dbo.Position;
import com.web.assistant.dbo.User;
import com.web.assistant.dbo.Worker;
import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import com.web.assistant.enumerated.PositionName;
import com.web.assistant.repository.AbstractRepository;
import com.web.assistant.repository.PositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService extends AbstractService<WorkerResponseDTO, WorkerRequestDTO, Worker> {

    private final UserService userService;
    private final AbstractRepository<Position> positionRepository;

    public WorkerService(final AbstractRepository<Worker> repository, final ModelMapper modelMapper,
                         final DtoConverter<WorkerResponseDTO, WorkerRequestDTO, Worker> dtoConverter,
                         final UserService userService, final AbstractRepository<Position> positionRepository) {
        super(repository, modelMapper, dtoConverter);
        this.userService = userService;
        this.positionRepository = positionRepository;
    }

    @Override
    public WorkerResponseDTO findOne(final long id) {
        return dtoConverter.convertToDto(repository.findById(id).orElseThrow());
    }

    @Override
    public List<WorkerResponseDTO> getList() {
        final List<Worker> workers = repository.findAll();
        return dtoConverter.convertToDto(workers);
    }

    @Override
    public WorkerResponseDTO create(final WorkerRequestDTO worker, final HttpServletRequest req) {
        final User user = userService.findMe(req);
        worker.setUser(user);
        final Worker dbo = dtoConverter.convertToDbo(worker);
        final List<PositionName> collect = dbo.getPositions().stream().map(Position::getPositionName).collect(Collectors.toList());
        final List<Position> positions = ((PositionRepository) positionRepository).findAllByPositionNameIn(collect);
        dbo.setPositions(positions);
        return dtoConverter.convertToDto(repository.save(dbo));
    }

    @Override
    public WorkerResponseDTO update(final WorkerRequestDTO responseDto, final HttpServletRequest req) {
        final User user = userService.findMe(req);
        final Worker oldWorker = user.getWorker();
        final Worker newWorker = dtoConverter.convertToDbo(responseDto);
        newWorker.setId(oldWorker.getId());
        newWorker.setUser(user);
        final List<PositionName> collect = newWorker.getPositions().stream().map(Position::getPositionName).collect(Collectors.toList());
        final List<Position> positions = ((PositionRepository) positionRepository).findAllByPositionNameIn(collect);
        newWorker.setPositions(positions);
        repository.save(newWorker);
        return dtoConverter.convertToDto(newWorker);
    }

    @Override
    @Transactional
    public void delete(final HttpServletRequest req) {
        final User user = userService.findMe(req);
        repository.delete(user.getWorker());
        user.setWorker(null);
    }

    public WorkerResponseDTO findMe(final HttpServletRequest req) {
        final Worker worker = userService.findMe(req).getWorker();
        return dtoConverter.convertToDto(worker);
    }

}
