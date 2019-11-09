package com.web.assistant.converter;

import com.web.assistant.dbo.Position;
import com.web.assistant.dto.request.PositionRequestDto;
import com.web.assistant.dto.response.PositionResponseDto;
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
