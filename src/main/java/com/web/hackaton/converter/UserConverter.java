package com.web.hackaton.converter;

import com.web.hackaton.dbo.User;
import com.web.hackaton.dto.request.UserRequestDTO;
import com.web.hackaton.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverter implements DtoConverter<UserResponseDTO, UserRequestDTO, User> {
    @Override
    public UserResponseDTO convertToDto(final User dbo) {
        final UserResponseDTO responseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(dbo, responseDTO);
        return responseDTO;
    }

    @Override
    public User convertToDbo(final UserRequestDTO dto) {
        final User user = new User();
        BeanUtils.copyProperties(dto,user);
        return user;
    }
}
