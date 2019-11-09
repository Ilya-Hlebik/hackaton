package com.web.assistant.repository;

import com.web.assistant.dbo.Skill;
import com.web.assistant.dbo.Worker;

import java.util.List;

public interface SkillRepository extends AbstractRepository<Skill> {
    void deleteAllByWorkerAndSkillNameNotIn(Worker worker, List<String> skillNames);

    List<Skill> findAllByWorker(Worker worker);
}
