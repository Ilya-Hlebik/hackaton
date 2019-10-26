package com.web.assistant.repository;

import com.web.assistant.dbo.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
}
