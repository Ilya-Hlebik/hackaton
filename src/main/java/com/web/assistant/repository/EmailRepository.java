package com.web.assistant.repository;

import com.web.assistant.dbo.EmailConfirmation;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailConfirmation, String> {
}
