package com.web.assistant.controller;

import com.web.assistant.dbo.*;
import com.web.assistant.dto.response.UserResponseDTO;
import com.web.assistant.enumerated.Role;
import com.web.assistant.enumerated.Status;
import com.web.assistant.repository.PositionRepository;
import com.web.assistant.repository.TechnologyRepository;
import com.web.assistant.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/temp")
@Api(tags = "temporary_features")
@AllArgsConstructor
public class TempController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TechnologyRepository technologyRepository;
    private final ModelMapper modelMapper;

    public static void main(final String[] args) {
        for (int i = 0; i<100 ; i++)
        System.out.println(new Random().nextInt(99));
    }

    @GetMapping("/createOneAccount")
    @ApiOperation(value = "Create account", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO createOneAccount() {
        User user = new User();
        user.setUsername("Test name" + new Random().nextInt());
        user.setPassword("Test pass" + new Random().nextInt());
        user.setEmail("Test email" + new Random().nextInt());
        user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final Worker worker = new Worker();
            worker.setUser(user);
            worker.setName("name" + new Random().nextInt(99));
            worker.setSureName("sureName" + new Random().nextInt(99));
            final List<Position> allPositions = positionRepository.findAll();
            final Position position = allPositions.get(new Random().nextInt(allPositions.size()));
            worker.setPositions(Collections.singletonList(position));
            worker.setStatus(Status.values()[new Random().nextInt(Status.values().length)]);
            worker.setPhoto("https://i.redd.it/pvxchv25rdb11.jpg");
            final List<Technology> technologies = technologyRepository.findAll();
            final Technology technology = technologies.get(new Random().nextInt(technologies.size()));
            worker.setTechnologies(Collections.singletonList(technology));
            worker.setSkills(List.of(new Skill("skill " + new Random().nextInt(), worker),
                    new Skill("skill " + new Random().nextInt(), worker)));
            user.setWorker(worker);
            user = userRepository.save(user);
        }
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
