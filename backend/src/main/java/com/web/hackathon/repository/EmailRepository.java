package com.web.hackathon.repository;

import com.web.hackathon.dbo.EmailConfirmation;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailConfirmation, String> {
}
