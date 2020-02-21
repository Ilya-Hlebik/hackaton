package com.web.hackaton.converter;

import com.web.hackaton.dbo.Position;
import com.web.hackaton.dto.request.PositionRequestDto;
import com.web.hackaton.dto.response.PositionResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PositionConverter implements DtoConverter<PositionResponseDto, PositionRequestDto, Position> {
    @Override
    public PositionResponseDto convertToDto(final Position dbo) {
        final PositionResponseDto positionResponseDto = new PositionResponseDto();
        BeanUtils.copyProperties(dbo, positionResponseDto);
        return positionResponseDto;
    }

    @Override
    public Position convertToDbo(final PositionRequestDto dto) {
        final Position position = new Position();
        BeanUtils.copyProperties(dto, position);
        return position;
    }
}
