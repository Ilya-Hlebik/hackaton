package com.web.assistant.controller;

import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.UserResponseDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import com.web.assistant.service.WorkerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/worker")
@Api(tags = "workers")
@AllArgsConstructor
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping("/all")
    @ApiOperation(value = "${WorkerController.findAll}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<WorkerResponseDTO> findAll() {
        return workerService.getList();
    }

    @PostMapping("/create")
    @ApiOperation(value = "${WorkerController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Probably you try to create more then 1 worker for 1 user"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public WorkerResponseDTO create(@ApiParam("Create Worker") @RequestBody final WorkerRequestDTO worker, final HttpServletRequest req) {
        return workerService.create(worker, req);
    }

    @PutMapping("/update")
    @ApiOperation(value = "${WorkerController.update}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public WorkerResponseDTO update(@ApiParam("Update Worker") @RequestBody final WorkerRequestDTO worker, final HttpServletRequest req) {
        return workerService.update(worker, req);
    }

    @GetMapping("/me")
    @ApiOperation(value = "${WorkerController.me}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The worker doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public WorkerResponseDTO me(final HttpServletRequest req) {
        return workerService.findOne(req);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "${WorkerController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The worker doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public void delete(final HttpServletRequest req) {
        workerService.delete(req);
    }

}
