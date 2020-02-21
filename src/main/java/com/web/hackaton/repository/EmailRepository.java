package com.web.hackaton.repository;

import com.web.hackaton.dbo.EmailConfirmation;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailConfirmation, String> {
}
