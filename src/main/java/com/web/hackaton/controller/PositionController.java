package com.web.hackaton.controller;

import com.web.hackaton.dto.request.PositionRequestDto;
import com.web.hackaton.dto.response.PositionResponseDto;
import com.web.hackaton.service.PositionService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
@Api(tags = "position")
@AllArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping("/all")
    @ApiOperation(value = "${PositionController.findAll}", response = PositionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<PositionResponseDto> findAll() {
        return positionService.getList();
    }

    @PostMapping("/create")
    @ApiOperation(value = "${PositionController.create}", response = PositionResponseDto.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Probably you try to create more then 1 worker for 1 user"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public PositionResponseDto create(@ApiParam("Create position") @RequestBody final PositionRequestDto position) {
        return positionService.create(position);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "${PositionController.delete}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The worker doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public void delete(@PathVariable final long id) {
        positionService.delete(id);
    }
}
