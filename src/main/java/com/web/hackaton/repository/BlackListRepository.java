package com.web.hackaton.repository;

import com.web.hackaton.dbo.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
}
