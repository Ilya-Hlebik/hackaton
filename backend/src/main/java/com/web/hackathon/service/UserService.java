package com.web.hackathon.service;

import com.web.hackathon.dbo.BlackList;
import com.web.hackathon.dbo.EmailConfirmation;
import com.web.hackathon.dbo.User;
import com.web.hackathon.dto.ForgotPasswordRequestDto;
import com.web.hackathon.dto.PasswordUpdateRequestDto;
import com.web.hackathon.dto.SignInRequestDTO;
import com.web.hackathon.dto.SignUpRequestDTO;
import com.web.hackathon.exception.CustomException;
import com.web.hackathon.repository.AbstractRepository;
import com.web.hackathon.repository.BlackListRepository;
import com.web.hackathon.repository.EmailRepository;
import com.web.hackathon.repository.UserRepository;
import com.web.hackathon.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.web.hackathon.security.JwtTokenProvider.ACCESS_TOKEN;
import static com.web.hackathon.security.JwtTokenProvider.REFRESH_TOKEN;

@Service
public class UserService extends AbstractService<User> {

    private final BlackListRepository blackListRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final EmailRepository emailRepository;

    public UserService(final AbstractRepository<User> repository, final ModelMapper modelMapper,
                       final BlackListRepository blackListRepository, final PasswordEncoder passwordEncoder,
                       final JwtTokenProvider jwtTokenProvider, final AuthenticationManager authenticationManager,
                       final EmailRepository emailRepository) {
        super(repository, modelMapper);
        this.blackListRepository = blackListRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.emailRepository = emailRepository;
    }

    public User signIn(final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
            final User user = ((UserRepository) repository).findByUsername(signInRequestDTO.getUsername());
            if (!user.isActive()) {
                throw new CustomException("Invalid password, please change", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
            jwtTokenProvider.createTokens(signInRequestDTO.getUsername(), user.getRoles(), res);
            return user;
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User signUp(final SignUpRequestDTO user, final boolean active) {
        if (!((UserRepository) repository).existsByUsername(user.getUsername())) {
            return repository.save(new User(user.getRoles(), user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()), active));
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(final String username) {
        ((UserRepository) repository).deleteByUsername(username);
    }

    public User search(final String username) {
        final User user = ((UserRepository) repository).findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User findMe(final HttpServletRequest req) {
        return ((UserRepository) repository).findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req).get(ACCESS_TOKEN), jwtTokenProvider.accessSecretKey));
    }

    public void logOut(final HttpServletRequest req) {
        final Map<String, String> stringStringMap = jwtTokenProvider.resolveToken(req);
        final String refreshToken = stringStringMap.get(REFRESH_TOKEN);
        final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtTokenProvider.refreshSecretKey).parseClaimsJws(refreshToken);
        final Date expiration = claimsJws.getBody().getExpiration();
        final Date now = new Date();
        final long liveTime = expiration.getTime() - now.getTime();
        blackListRepository.save(new BlackList(refreshToken, liveTime));
    }

    public User updatePassword(final PasswordUpdateRequestDto updateRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updateRequestDto.getUsername(), updateRequestDto.getOldPassword()));
            if (!updateRequestDto.getNewPasswordFirstEntry().equals(updateRequestDto.getNewPasswordSecondEntry())) {
                throw new CustomException("Passwords do not match", HttpStatus.BAD_REQUEST);
            }
            if (updateRequestDto.getNewPasswordFirstEntry().equals(updateRequestDto.getOldPassword())) {
                throw new CustomException("Old password and new password shouldn't be the same", HttpStatus.BAD_REQUEST);
            }
            final User user = ((UserRepository) repository).findByUsername(updateRequestDto.getUsername());
            user.setPassword(passwordEncoder.encode(updateRequestDto.getNewPasswordFirstEntry()));
            user.setActive(true);
            return repository.save(user);
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<User> updatePassword(final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        final Optional<User> userOptional = ((UserRepository) repository).findByEmail(forgotPasswordRequestDto.getEmail());
        final Optional<EmailConfirmation> confirmationOptional = emailRepository.findById(forgotPasswordRequestDto.getEmail());
        if (userOptional.isPresent() && confirmationOptional.isPresent() && confirmationOptional.get().isActive()) {
            final User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(forgotPasswordRequestDto.getNewPassword()));
            repository.save(user);
            emailRepository.delete(confirmationOptional.get());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
