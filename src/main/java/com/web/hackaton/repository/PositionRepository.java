package com.web.hackaton.repository;

import com.web.hackaton.dbo.Position;

import java.util.List;

public interface PositionRepository extends AbstractRepository<Position> {
    List<Position> findAllByPositionNameIn(List<String> positionNames);
}
