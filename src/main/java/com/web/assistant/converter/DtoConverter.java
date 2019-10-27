package com.web.assistant.converter;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public interface DtoConverter<Response, Request, Entity> {
    Response convertToDto(final Entity dbo);

    Entity convertToDbo(final Request dto);

    default Set<Response> convertToDto(final Set<Entity> dbo) {
        return dbo != null ? dbo.stream().map(this::convertToDto).collect(Collectors.toSet()) : null;
    }

    default Set<Entity> convertToDbo(final Set<Request> dto) {
        return dto != null ? dto.stream().map(this::convertToDbo).collect(Collectors.toSet()) : null;
    }

    default List<Response> convertToDto(final List<Entity> dbo) {
        return dbo != null ? dbo.stream().map(this::convertToDto).collect(Collectors.toList()) : null;
    }

    default List<Entity> convertToDbo(final List<Request> dto) {
        return dto != null ? dto.stream().map(this::convertToDbo).collect(Collectors.toList()) : null;
    }
}
