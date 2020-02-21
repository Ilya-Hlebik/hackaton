package com.web.hackaton.controller;

import com.web.hackaton.dto.response.SkillResponseDto;
import com.web.hackaton.service.SkillService;
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
@RequestMapping("/skill")
@Api(tags = "skill")
@AllArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/all")
    @ApiOperation(value = "${SkillController.findAll}", response = SkillResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<SkillResponseDto> findAll() {
        return skillService.getList();
    }
}
