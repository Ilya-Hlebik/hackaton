package com.web.assistant.repository;

import com.web.assistant.dbo.Position;
import com.web.assistant.enumerated.PositionName;

import java.util.List;

public interface PositionRepository extends AbstractRepository<Position> {
    List<Position> findAllByPositionNameIn(List<PositionName> positionNames);
}
