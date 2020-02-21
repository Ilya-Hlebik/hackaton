package com.web.hackaton.repository;

import com.web.hackaton.dbo.Technology;

import java.util.List;

public interface TechnologyRepository extends AbstractRepository<Technology> {
    List<Technology> findAllByTechnologyNameIn(List<String> positionNames);
}
