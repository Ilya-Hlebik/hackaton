package com.web.assistant.repository;

import com.web.assistant.dbo.Technology;

import java.util.List;

public interface TechnologyRepository extends AbstractRepository<Technology> {
    List<Technology> findAllByTechnologyNameIn(List<String> positionNames);
}
