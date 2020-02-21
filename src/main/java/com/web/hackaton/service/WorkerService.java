package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.User;
import com.web.hackaton.dbo.Worker;
import com.web.hackaton.dto.request.WorkerRequestDTO;
import com.web.hackaton.dto.response.WorkerResponseDTO;
import com.web.hackaton.repository.AbstractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class WorkerService extends AbstractService<WorkerResponseDTO, WorkerRequestDTO, Worker> {

    private final UserService userService;
    private final TechnologyService technologyService;
    private final PositionService positionService;
    private final SkillService skillService;

    public WorkerService(final AbstractRepository<Worker> repository, final ModelMapper modelMapper,
                         final DtoConverter<WorkerResponseDTO, WorkerRequestDTO, Worker> dtoConverter,
                         final UserService userService, final TechnologyService technologyService,
                         final PositionService positionService, final SkillService skillService) {
        super(repository, modelMapper, dtoConverter);
        this.userService = userService;
        this.technologyService = technologyService;
        this.positionService = positionService;
        this.skillService = skillService;
    }

    public WorkerResponseDTO findOne(final long id) {
        return dtoConverter.convertToDto(repository.findById(id).orElseThrow());
    }

    public List<WorkerResponseDTO> getList() {
        final List<Worker> workers = repository.findAll();
        return dtoConverter.convertToDto(workers);
    }

    public WorkerResponseDTO create(final WorkerRequestDTO worker, final HttpServletRequest req) {
        final User user = userService.findMe(req);
        worker.setUser(user);
        final Worker dbo = dtoConverter.convertToDbo(worker);
        dbo.setPositions(positionService.findPositionsByNames(dbo.getPositions()));
        dbo.setTechnologies(technologyService.findTechnologiesByNames(dbo.getTechnologies()));
        dbo.getSkills().forEach(skill -> skill.setWorker(dbo));
        return dtoConverter.convertToDto(repository.save(dbo));
    }


    public WorkerResponseDTO update(final WorkerRequestDTO requestDTO, final HttpServletRequest req) {
        final User user = userService.findMe(req);
        final Worker oldWorker = user.getWorker();
        final Worker newWorker = dtoConverter.convertToDbo(requestDTO);
        oldWorker.setPhoto(newWorker.getPhoto());
        oldWorker.setName(newWorker.getName());
        oldWorker.setSureName(newWorker.getSureName());
        oldWorker.setStatus(newWorker.getStatus());
        skillService.updateSkills(oldWorker, newWorker);
        oldWorker.setTechnologies(technologyService.findTechnologiesByNames(newWorker.getTechnologies()));
        oldWorker.setPositions(positionService.findPositionsByNames(newWorker.getPositions()));
        repository.save(oldWorker);
        return dtoConverter.convertToDto(newWorker);
    }

    @Transactional
    public void delete(final HttpServletRequest req) {
        final User user = userService.findMe(req);
        repository.delete(user.getWorker());
        user.setWorker(null);
    }

    public WorkerResponseDTO findMe(final HttpServletRequest req) {
        final Worker worker = userService.findMe(req).getWorker();
        if (worker == null) {
            return null;
        }
        return dtoConverter.convertToDto(worker);
    }
}
