package com.web.assistant.controller;

import com.web.assistant.dto.request.TechnologyRequestDto;
import com.web.assistant.dto.response.TechnologyResponseDto;
import com.web.assistant.service.TechnologyService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technology")
@Api(tags = "technology")
@AllArgsConstructor
public class TechnologyController {
    private final TechnologyService technologyService;

    @GetMapping("/all")
    @ApiOperation(value = "${TechnologyController.findAll}", response = TechnologyResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<TechnologyResponseDto> findAll() {
        return technologyService.getList();
    }

    @PostMapping("/create")
    @ApiOperation(value = "${TechnologyController.create}", response = TechnologyResponseDto.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Probably you try to create more then 1 worker for 1 user"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public TechnologyResponseDto create(@ApiParam("Create position") @RequestBody final TechnologyRequestDto technology) {
        return technologyService.create(technology);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "${TechnologyController.delete}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The worker doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public void delete(@PathVariable final long id) {
        technologyService.delete(id);
    }
}
