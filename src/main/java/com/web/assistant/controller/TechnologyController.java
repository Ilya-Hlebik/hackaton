package com.web.assistant.controller;

import com.web.assistant.dto.response.TechnologyResponseDto;
import com.web.assistant.service.TechnologyService;
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
}
