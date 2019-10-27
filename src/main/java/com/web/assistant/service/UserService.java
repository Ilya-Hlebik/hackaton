package com.web.assistant.service;

import com.web.assistant.dbo.BlackList;
import com.web.assistant.dbo.User;
import com.web.assistant.dto.request.SignInRequestDTO;
import com.web.assistant.exception.CustomException;
import com.web.assistant.repository.AbstractRepository;
import com.web.assistant.repository.BlackListRepository;
import com.web.assistant.repository.UserRepository;
import com.web.assistant.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

import static com.web.assistant.security.JwtTokenProvider.ACCESS_TOKEN;
import static com.web.assistant.security.JwtTokenProvider.REFRESH_TOKEN;

@Service
@AllArgsConstructor
public class UserService {

    private final AbstractRepository repository;

    private final BlackListRepository blackListRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public String signIn(final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
            return jwtTokenProvider.createTokens(signInRequestDTO.getUsername(), ((UserRepository) repository).findByUsername(signInRequestDTO.getUsername()).getRoles(), res).get(ACCESS_TOKEN);
        } catch (final AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signUp(final User user, final HttpServletResponse res) {
        if (!((UserRepository) repository).existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            ((UserRepository) repository).save(user);
            return jwtTokenProvider.createTokens(user.getUsername(), user.getRoles(), res).get(ACCESS_TOKEN);
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
}
