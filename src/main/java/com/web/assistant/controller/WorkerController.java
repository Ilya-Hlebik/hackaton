package com.web.assistant.controller;

import com.web.assistant.dto.request.WorkerRequestDTO;
import com.web.assistant.dto.response.UserResponseDTO;
import com.web.assistant.dto.response.WorkerResponseDTO;
import com.web.assistant.service.WorkerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
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
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public WorkerResponseDTO create(@ApiParam("Create Worker") @RequestBody final WorkerRequestDTO worker, final HttpServletRequest req) {
        return workerService.create(worker, req);
    }
}
