package com.web.hackaton.repository;

import com.web.hackaton.dbo.Skill;
import com.web.hackaton.dbo.Worker;

import java.util.List;

public interface SkillRepository extends AbstractRepository<Skill> {
    void deleteAllByWorkerAndSkillNameNotIn(Worker worker, List<String> skillNames);

    List<Skill> findAllByWorker(Worker worker);
}
