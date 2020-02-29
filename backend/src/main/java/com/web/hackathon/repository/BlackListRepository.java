package com.web.hackathon.repository;

import com.web.hackathon.dbo.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
}
