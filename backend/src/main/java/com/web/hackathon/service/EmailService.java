package com.web.hackathon.service;

import com.web.hackathon.dbo.EmailConfirmation;
import com.web.hackathon.dbo.User;
import com.web.hackathon.dto.EmailConfirmationDto;
import com.web.hackathon.repository.EmailRepository;
import com.web.hackathon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    public ResponseEntity sendVerifyCodeByEmail(final String email) {
        final Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        final int verifyCode = ThreadLocalRandom.current().nextInt(1111, 9999);
        final EmailConfirmation emailConfirmation = new EmailConfirmation(email, verifyCode, false);
        emailRepository.save(emailConfirmation);
        final SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("assistant.noreply.mail@gmail.com");
        msg.setTo(email);
        msg.setSubject("Assistant - confirm your password recovery");
        msg.setText("Hello \n" +
                "Please enter this code in application to verify identity:\n"
                + verifyCode);

        javaMailSender.send(msg);
        return ResponseEntity.ok().build();
    }

    public boolean verifyCode(final EmailConfirmationDto emailConfirmationDto) {
        final Optional<EmailConfirmation> confirmationOptional = emailRepository.findById(emailConfirmationDto.getEmail());
        if (confirmationOptional.isPresent()) {
            final boolean codeWasCorrect = confirmationOptional.orElseThrow().getConfirmationCode() == emailConfirmationDto.getCode();
            if (codeWasCorrect) {
                confirmationOptional.get().setActive(true);
                emailRepository.save(confirmationOptional.get());
            } else {
                emailRepository.delete(confirmationOptional.get());
            }
            return codeWasCorrect;
        }
        return false;
    }
}
