package com.web.hackaton.service;

import com.web.hackaton.converter.DtoConverter;
import com.web.hackaton.dbo.BlackList;
import com.web.hackaton.dbo.EmailConfirmation;
import com.web.hackaton.dbo.User;
import com.web.hackaton.dto.request.ForgotPasswordRequestDto;
import com.web.hackaton.dto.request.PasswordUpdateRequestDto;
import com.web.hackaton.dto.request.SignInRequestDTO;
import com.web.hackaton.dto.request.UserRequestDTO;
import com.web.hackaton.dto.response.UserResponseDTO;
import com.web.hackaton.exception.CustomException;
import com.web.hackaton.repository.AbstractRepository;
import com.web.hackaton.repository.BlackListRepository;
import com.web.hackaton.repository.EmailRepository;
import com.web.hackaton.repository.UserRepository;
import com.web.hackaton.security.JwtTokenProvider;
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

import static com.web.hackaton.security.JwtTokenProvider.ACCESS_TOKEN;
import static com.web.hackaton.security.JwtTokenProvider.REFRESH_TOKEN;

@Service
public class UserService extends AbstractService<UserResponseDTO, UserRequestDTO, User> {

    private final BlackListRepository blackListRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final EmailRepository emailRepository;

    public UserService(final AbstractRepository<User> repository, final ModelMapper modelMapper,
                       final DtoConverter<UserResponseDTO, UserRequestDTO, User> dtoConverter,
                       final BlackListRepository blackListRepository, final PasswordEncoder passwordEncoder,
                       final JwtTokenProvider jwtTokenProvider, final AuthenticationManager authenticationManager,
                       final EmailRepository emailRepository) {
        super(repository, modelMapper, dtoConverter);
        this.blackListRepository = blackListRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.emailRepository = emailRepository;
    }

    public UserResponseDTO signIn(final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
            final User user = ((UserRepository) repository).findByUsername(signInRequestDTO.getUsername());
            if (!user.isActive()) {
                throw new CustomException("Invalid password, please change", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
            jwtTokenProvider.createTokens(signInRequestDTO.getUsername(), user.getRoles(), res);
            return dtoConverter.convertToDto(user);
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public UserResponseDTO signUp(final User user, final boolean active) {
        if (!((UserRepository) repository).existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(active);
            return dtoConverter.convertToDto(repository.save(user));
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

    public UserResponseDTO updatePassword(final PasswordUpdateRequestDto updateRequestDto) {
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
            return dtoConverter.convertToDto(repository.save(user));
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<UserResponseDTO> updatePassword(final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        final Optional<User> userOptional = ((UserRepository) repository).findByEmail(forgotPasswordRequestDto.getEmail());
        final Optional<EmailConfirmation> confirmationOptional = emailRepository.findById(forgotPasswordRequestDto.getEmail());
        if (userOptional.isPresent() && confirmationOptional.isPresent() && confirmationOptional.get().isActive()) {
            final User user = userOptional.get();
            user.setPassword(forgotPasswordRequestDto.getNewPassword());
            repository.save(user);
            emailRepository.delete(confirmationOptional.get());
            return ResponseEntity.ok(dtoConverter.convertToDto(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
