package com.web.assistant.controller;

import com.web.assistant.dto.response.PositionResponseDto;
import com.web.assistant.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
